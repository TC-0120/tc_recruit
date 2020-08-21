package jp.co.tc.recruit.repository.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.view.V_Candidates;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidatesViewRepository;
import jp.co.tc.recruit.repository.CandidatesViewRepositoryCustom;

/**
 * 候補者情報ビューのリポジトリのカスタムメソッド実装クラス
 *
 * @author TC-0115
 *
 */
public class CandidatesViewRepositoryImpl implements CandidatesViewRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	CandidatesViewRepository cddViewRepo;

	/**
	 * 選考ステータス、詳細、選考日程検索
	 *
	 * @param cf 検索条件
	 * @return 候補者情報
	 */
	@SuppressWarnings("unchecked")
	public List<V_Candidates> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf) {
		Integer ssId = cf.getSlcStatusId();
		Integer ssdId = cf.getSlcStatusDtlId();
		String from = cf.getFrom();
		String to = cf.getTo();
		Integer fsId = cf.getFreeSearchId();
		String fsWord = cf.getFreeSearchWord();

		//WHERE句の初めかどうかのフラグ
		boolean firstFlag = true;

		//SQL文
		String queryStr = "from V_Candidates";

		//条件が入力されていない場合、全件検索、並び替えをして結果を返す
		if (ssId == SlcStatusConstant.ALL && ssdId == SlcStatusDtlConstant.ALL && from.isEmpty() && to.isEmpty()
				&& fsId == ConditionsForm.SEARCH_NO_SELECT) {
			//並び替え
			queryStr += " WHERE slcStatusDtlId NOT IN(4,5,9) AND deleteFlag = :notdeleted " + sort(cf.getOrder(), cf.getDirection());
			// クエリを発行
			Query query = em.createQuery(queryStr);
			query.setParameter("notdeleted", DeleteFlagConstant.NOT_DELETED);

			return query.getResultList();
		}

		//以下、WHERE句が存在する場合
		queryStr += " WHERE";

		//選択したステータスが一覧でない場合
		if (ssId != SlcStatusConstant.ALL) {
			//WHERE句の初めでない場合
			if (!firstFlag) {
				queryStr += " AND";
			}
			firstFlag = false;

			//SQL文を追加AND slcStatusDtlId NOT IN(4,5,9)
			queryStr += " slcStatusId = :ssId ";
		}

		//選択したステータス詳細が一覧でない場合
		if (ssdId != SlcStatusDtlConstant.ALL) {
			//WHERE句の初めでない場合
			if (!firstFlag) {
				queryStr += " AND";
			}
			firstFlag = false;

			if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
				//選択したステータス詳細が要対応の場合
				queryStr += " (slcStatusDtlId = :adjusting OR slcStatusDtlId = :passing OR slcStatusDtlId = :acceptance "
						+ "OR ((slcStatusDtlId = :selecting OR slcStatusDtlId = :waitingAcceptance OR slcStatusDtlId = :confirmed) "
						+ "AND slcDate < :today))";
			} else {
				//選択したステータス詳細が一覧、要対応以外の場合
				queryStr += " slcStatusDtlId = :ssdId";
			}
		}

		//自由検索が入力されている場合
		//適性検査等必要な部分を追加する
		if (fsId != ConditionsForm.SEARCH_NO_SELECT) {
			//WHERE句の初めでない場合
			if (!firstFlag) {
				queryStr += " AND";
			}
			firstFlag = false;

			switch (fsId) {
			case ConditionsForm.SEARCH_CANDIDATE_NAME:
				queryStr += " (candidateName LIKE :keyword OR candidateFurigana LIKE :keyword)";
				break;
			case ConditionsForm.SEARCH_CANDIDATE_NAME_FURIGANA:
				queryStr += " (candidateFurigana LIKE :keyword)";
				break;
			case ConditionsForm.SEARCH_EDU_BACK:
				queryStr += " eduBack LIKE :keyword OR universityName LIKE :keyword "
						+ "OR facultyName LIKE :keyword OR departmentName LIKE :keyword";
				break;
			case ConditionsForm.SEARCH_AGENT:
				queryStr += " agentName LIKE :keyword";
				break;
			case ConditionsForm.SEARCH_APTITUDE:
				queryStr += " aptitudeStatus LIKE :keyword";
				break;
			case ConditionsForm.DELETE_STATUS:
				queryStr += " deleteFlag LIKE :keyword";
				break;
			default:
				break;
			}

		}

		//日程検索が入力されている場合
		if (!from.isEmpty() || !to.isEmpty()) {
			//WHERE句の初めでない場合
			if (!firstFlag) {
				queryStr += " AND";
			}

			//日付が表示されるステータス詳細を条件に追加
			//queryStr += " ((slcStatusDtlId = :selectingDate OR slcStatusDtlId = :waitingAcceptanceDate OR slcStatusDtlId = :confirmedDate)";


			if (!from.isEmpty() && !to.isEmpty()) {
				//fromとtoが入力されている場合
				queryStr += " slcDate >= :from AND slcDate < :to";//AND削除,後部に追加
			}else if(to.isEmpty()) {
				//fromだけ入力されている場合
				queryStr += " slcDate >= :from";
			}else {
				//toだけ入力されている場合
				queryStr += " slcDate < :to";//AND削除
			}

			//			//toが入力されている場合
			//			if (!to.isEmpty()) {
			//				queryStr += " slcDate < :to";//AND削除
			//			}

			//queryStr += ")";
		}
		//削除フラグ設定
		queryStr += " AND deleteFlag = :notdeleted";
		//全件表示の場合の除外要件追加
		if(ssId == SlcStatusConstant.ALL && ssdId == SlcStatusDtlConstant.ALL) {
			queryStr += " AND slcStatusDtlId NOT IN(4,5,9)";
		}
		//並び替え
		queryStr += sort(cf.getOrder(), cf.getDirection());

		// クエリを発行
		Query query = em.createQuery(queryStr);

		//プレースホルダに値をセット

		//選択したステータスが一覧でない場合
		if (ssId != SlcStatusConstant.ALL) {
			query.setParameter("ssId", ssId);
		}

		if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
			//選択したステータス詳細が要対応の場合
			query.setParameter("adjusting", SlcStatusDtlConstant.ADJUSTING)
					.setParameter("passing", SlcStatusDtlConstant.PASSING)
					.setParameter("acceptance", SlcStatusDtlConstant.ACCEPTANCE)
					.setParameter("selecting", SlcStatusDtlConstant.SELECTING)
					.setParameter("waitingAcceptance", SlcStatusDtlConstant.WATING_ACCEPTANCE)
					.setParameter("confirmed", SlcStatusDtlConstant.CONFIRMED)
					.setParameter("today", Calendar.getInstance().getTime());

		} else if (ssdId != SlcStatusDtlConstant.ALL) {
			//選択したステータス詳細が一覧、要対応以外の場合
			query.setParameter("ssdId", ssdId);
		}

		//自由検索が入力されている場合
		if (fsId != ConditionsForm.SEARCH_NO_SELECT) {
			query.setParameter("keyword", "%" + fsWord + "%");
		}

		//日程検索が入力されている場合
//		if (!from.isEmpty() || !to.isEmpty()) {
//			query.setParameter("selectingDate", SlcStatusDtlConstant.SELECTING)
//					.setParameter("waitingAcceptanceDate", SlcStatusDtlConstant.WATING_ACCEPTANCE)
//					.setParameter("confirmedDate", SlcStatusDtlConstant.CONFIRMED);
//		}

		try {

			//日付のフォーマットを指定（時刻を切り捨てる）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			//fromが入力されている場合
			if (!from.isEmpty()) {
				Date fromDate = sdf.parse(from.replace("T", " "));
				query.setParameter("from", fromDate);
			}

			//toが入力されている場合
			if (!to.isEmpty()) {
				Date toDate = sdf.parse(to.replace("T", " "));
				query.setParameter("to", toDate);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		//削除フラグ設定
		query.setParameter("notdeleted", DeleteFlagConstant.NOT_DELETED);

		return query.getResultList();

	}

	/**
	 * 追加するORDER BY～のSQL文を返すメソッド
	 *
	 * @param order ソート項目ID
	 * @param dir 順番ID
	 * @return SQL文（ORDER BY～）
	 */
	private String sort(Integer order, Integer dir) {
		String queryStr = "";

		//orderの値に応じて、ORDER BYを指定
		//slcDateはサービスクラスでさらに並び替える必要あり
		//（合格など一覧に表示されない日付がデータベースに入っているため）
		switch (order) {
		case ConditionsForm.CANDIDATE_ID:
			queryStr += " ORDER BY candidateId";
			break;
		case ConditionsForm.CANDIDATE_NAME:
			queryStr += " ORDER BY candidateFurigana";
			break;
		case ConditionsForm.CANDIDATE_NAME_FURIGANA:
			queryStr += " ORDER BY candidateFurigana";
			break;
		case ConditionsForm.GENDER:
			queryStr += " ORDER BY gender";
			break;
		case ConditionsForm.EDU_BACK:
			queryStr += " ORDER BY eduBack";
			break;
		case ConditionsForm.CANDIDATE_EMAIL_ADDRESS:
			queryStr += " ORDER BY candidateEmailAddress";
			break;
		case ConditionsForm.AGENT:
			queryStr += " ORDER BY agentId";
			break;
		case ConditionsForm.AGENT_FEE:
			queryStr += " ORDER BY agentFee";
			break;
		case ConditionsForm.APTITUDE:
			queryStr += " ORDER BY aptitudeId";
			break;
		case ConditionsForm.APTITUDE_SCORE:
			queryStr += " ORDER BY aptitudeScore";
			break;
		case ConditionsForm.SLC_STATUS:
			queryStr += " ORDER BY selectionProcedure";
			break;
		case ConditionsForm.SLC_STATUS_DTL:
			queryStr += " ORDER BY slcStatusDtlId";
			break;
		case ConditionsForm.SLC_DATE:
			queryStr += " ORDER BY slcDate";
			break;
		case ConditionsForm.REMARKS:
			queryStr += " ORDER BY remarks";
			break;
		case ConditionsForm.INSERT_USER:
			queryStr += " ORDER BY insertUser";
			break;
		case ConditionsForm.INSERT_DATE:
			queryStr += " ORDER BY insertDate";
			break;
		case ConditionsForm.UPDATE_USER:
			queryStr += " ORDER BY updateUser";
			break;
		case ConditionsForm.UPDATE_DATE:
			queryStr += " ORDER BY updateDate";
			break;
		case ConditionsForm.DELETE_FLAG:
			queryStr += " ORDER BY deleteFlag";
			break;
		default:
			break;
		}

		//並び替えの順番を指定
		if (dir == ConditionsForm.DESC) {
			//降順の場合
			//学歴マスタの内容を参照
			if(order == ConditionsForm.EDU_BACK) {
				queryStr += " DESC ,universityName DESC, facultyName DESC, departmentName DESC";
			}else {
				queryStr += " DESC";
			}

		}else {
			if(order == ConditionsForm.EDU_BACK) {
				//学歴マスタの内容を参照
				queryStr += ", universityName DESC, facultyName DESC, departmentName";
			}
		}

		return queryStr;
	}

	/*
	 * 選考日程検索メソッド
	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcDate(String from, String to) {
			String queryStr = "from CandidatesView WHERE (slcStatusDtlId = 2 OR slcStatusDtlId = 6 OR slcStatusDtlId = 8) AND slcDate ";

			if (!from.isEmpty() && !to.isEmpty()) {
				queryStr += "BETWEEN :from AND :to ORDER BY slcDate";
			} else if (from.isEmpty() && !to.isEmpty()) {
				queryStr += "< :to ORDER BY slcDate";
			} else if (!from.isEmpty() && to.isEmpty()) {
				queryStr += ">= :from ORDER BY slcDate";
			}

			Query query = em.createQuery(queryStr);

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				if (!from.isEmpty()) {
					query.setParameter("from", sdf.parse(from));
				}

				if (!to.isEmpty()) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(sdf.parse(to));
					cal.add(Calendar.DAY_OF_MONTH, 1);
					query.setParameter("to", cal.getTime());
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

			return query.getResultList();
	}
	*/

	/*
	 * 選択ステータス、詳細検索メソッド
	@SuppressWarnings("unchecked")
	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlId(ConditionsForm cf) {
			Integer ssId = cf.getSlcStatusId();
			Integer ssdId = cf.getSlcStatusDtlId();
			Integer order = cf.getOrder();
			Integer dir = cf.getDirection();

			String queryStr = "from CandidatesView";

			if (ssdId == SlcStatusDtlConstant.ALL) {

				if (ssId != SlcStatusConstant.ALL) {
					queryStr += " WHERE slcStatusId = :ssId";
				}
			} else if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
				queryStr += " WHERE slcStatusDtlId = :adjusting OR slcStatusDtlId = :passing OR slcStatusDtlId = :acceptance "
						+ "OR ((slcStatusDtlId = :selecting OR slcStatusDtlId = :waitingAcceptance OR slcStatusDtlId = :confirmed) "
						+ "AND slcDate < :today)";

				if (ssId != SlcStatusConstant.ALL) {
					queryStr += " AND slcStatusId = :ssId";
				}
			} else {
				queryStr += " WHERE slcStatusDtlId = :ssdId";

			if (ssId != SlcStatusConstant.ALL) {
				queryStr += " AND slcStatusId = :ssId";
			}
		}

		switch (order) {
			case ConditionsForm.CANDIDATE_ID:
				queryStr += " ORDER BY candidateId";
				break;
			case ConditionsForm.GENDER:
				queryStr += " ORDER BY gender";
				break;
			case ConditionsForm.AGENT:
				queryStr += " ORDER BY agentId";
				break;
			case ConditionsForm.REFERRER:
				queryStr += " ORDER BY referrerId";
				break;
			case ConditionsForm.SLC_STATUS:
				queryStr += " ORDER BY slcStatusId";
				break;
			case ConditionsForm.SLC_STATUS_DTL:
				queryStr += " ORDER BY slcStatusDtlId";
				break;
			case ConditionsForm.SLC_DATE:
				queryStr += " ORDER BY slcDate";
			default:
				break;
		}

		if (dir == ConditionsForm.DESC) {
			queryStr += " DESC";
		}

		Query query = em.createQuery(queryStr);

		if (ssId != SlcStatusConstant.ALL) {
			query.setParameter("ssId", ssId);
		}

		if (ssdId == SlcStatusDtlConstant.NEED_RESPOND) {
			query.setParameter("adjusting", SlcStatusDtlConstant.ADJUSTING)
				 .setParameter("passing", SlcStatusDtlConstant.PASSING)
				 .setParameter("acceptance", SlcStatusDtlConstant.ACCEPTANCE)
				 .setParameter("selecting", SlcStatusDtlConstant.SELECTING)
				 .setParameter("waitingAcceptance", SlcStatusDtlConstant.WATING_ACCEPTANCE)
				 .setParameter("confirmed", SlcStatusDtlConstant.CONFIRMED)
				 .setParameter("today", Calendar.getInstance().getTime());
		} else if (ssdId != SlcStatusDtlConstant.ALL) {
			query.setParameter("ssdId", ssdId);
		}


		return query.getResultList();
	}
	*/
}
