package jp.co.tc.recruit.repository.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.CandidatesView;
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
	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf) {
		Integer ssId = cf.getSlcStatusId();
		Integer ssdId = cf.getSlcStatusDtlId();
		String from = cf.getFrom();
		String to = cf.getTo();
		Integer fsId = cf.getFreeSearchId();
		String fsWord = cf.getFreeSearchWord();

		//WHERE句の初めかどうかのフラグ
		boolean firstFlag = true;

		//SQL文
		String queryStr = "from CandidatesView";

		//条件が入力されていない場合、全件検索、並び替えをして結果を返す
		if (ssId == SlcStatusConstant.ALL && ssdId == SlcStatusDtlConstant.ALL && from.isEmpty() && to.isEmpty()
				&& fsId == ConditionsForm.SEARCH_NO_SELECT) {
			//並び替え
			queryStr += " WHERE slcStatusDtlId NOT IN(4,5,9)" + sort(cf.getOrder(), cf.getDirection());

			return em.createQuery(queryStr).getResultList();
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

			//SQL文を追加
			queryStr += " slcStatusId = :ssId AND slcStatusDtlId NOT IN(4,5,9)";
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
			case ConditionsForm.SEARCH_EDU_BACK:
				queryStr += " eduBack LIKE :keyword";
				break;
			case ConditionsForm.SEARCH_AGENT:
				queryStr += " agentName LIKE :keyword";
				break;
			case ConditionsForm.SEARCH_REFERRER:
				queryStr += " referrerName LIKE :keyword";
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
			queryStr += " ((slcStatusDtlId = :selectingDate OR slcStatusDtlId = :waitingAcceptanceDate OR slcStatusDtlId = :confirmedDate)";

			//fromが入力されている場合
			if (!from.isEmpty()) {
				queryStr += " AND slcDate >= :from";
			}

			//toが入力されている場合
			if (!to.isEmpty()) {
				queryStr += " AND slcDate < :to";
			}

			queryStr += ")";
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
		if (!from.isEmpty() || !to.isEmpty()) {
			query.setParameter("selectingDate", SlcStatusDtlConstant.SELECTING)
					.setParameter("waitingAcceptanceDate", SlcStatusDtlConstant.WATING_ACCEPTANCE)
					.setParameter("confirmedDate", SlcStatusDtlConstant.CONFIRMED);
		}

		try {

			//日付のフォーマットを指定（時刻を切り捨てる）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			//fromが入力されている場合
			if (!from.isEmpty()) {
				query.setParameter("from", sdf.parse(from.replace("T", " ")));
				System.out.println(sdf.parse(from.replace("T", " ")));
			}

			//toが入力されている場合
			if (!to.isEmpty()) {
				//日付を取得
				Calendar cal = Calendar.getInstance();
				//入力値をDate型に変換、代入
				cal.setTime(sdf.parse(to.replace("T", " ")));
				//日付を一日足す（足さないと入力日が含まれない）
				cal.add(Calendar.DAY_OF_MONTH, 1);
				query.setParameter("to", cal.getTime());
				System.out.println(cal.getTime());
			}

			/*//日付を取得
			Calendar cal = Calendar.getInstance();
			String toT = to.replace("T", " ");
			//入力値をDate型に変換、代入
			cal.setTime(sdf.parse(toT));
			//日付を一日足す（足さないと入力日が含まれない）
			cal.add(Calendar.DAY_OF_MONTH, 1);
			System.out.println(cal.getTime());
			String toStr = sdf.format(cal.getTime()).replace("T", " ");
			query.setParameter("to", to_char(slcDate, 'yyyy-MM-dd HH24:mm') = sdf.format(cal.getTime()).replace("T", " "));*/

			/*//日付のフォーマットを指定（時刻を切り捨てる）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			//fromが入力されている場合
			if (!from.isEmpty()) {
				String matchPatternFrom = from.replace("T", " ");
				Date sdfStr = sdf.parse(str);
				query.setParameter("from", matchPatternFrom);
				System.out.println(matchPatternFrom);
			}

			//toが入力されている場合
			if (!to.isEmpty()) {
				//日付を取得
				Calendar cal = Calendar.getInstance();
				//入力値をDate型に変換、代入
				cal.setTime(sdf.parse(to));
				//日付を一日足す（足さないと入力日が含まれない）
				cal.add(Calendar.DAY_OF_MONTH, 1);
				String matchPatternFrom = to.replace("T", " ");
				query.setParameter("to", cal.getTime());
				System.out.println(cal.getTime());
			}*/

		} catch (ParseException e) {
			e.printStackTrace();
		}

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
		case ConditionsForm.GENDER:
			queryStr += " ORDER BY gender";
			break;
		case ConditionsForm.EDU_BACK:
			queryStr += " ORDER BY eduBack";
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

		//並び替えの順番を指定
		if (dir == ConditionsForm.DESC) {
			//降順の場合
			queryStr += " DESC";
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
