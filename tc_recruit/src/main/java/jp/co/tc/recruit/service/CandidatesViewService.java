package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidatesViewRepository;
import jp.co.tc.recruit.view.CandidatesView;

/**
 * 候補者情報ビューのサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class CandidatesViewService {

	@Autowired
	CandidatesViewRepository repo;

	public List<CandidatesView> findAll() {
		return repo.findAll();
	}

	/**
	 * 候補者情報の検索
	 *
	 * @param cf 検索条件
	 * @return 候補者情報
	 */
	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf) {
		List<CandidatesView> cv = new ArrayList<CandidatesView>();

		//検索条件から候補者情報を取得
		cv = repo.findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(cf);

		//ソート項目が選考日程の場合、Comparatorクラスを用いて並び替え
		if (cf.getOrder() == ConditionsForm.SLC_DATE) {
			Collections.sort(cv, new SlcDateComparator());

			if (cf.getDirection() == ConditionsForm.DESC) {
				Collections.reverse(cv);
			}
		}


		return cv;
	}

	public List<CandidatesView> getCandidatesByCandidateId(Integer[] cId) {
		List<CandidatesView> cv = new ArrayList<CandidatesView>();
		for (int i = 0; i < cId.length; i++) {
			cv.add(repo.findByCandidateId(cId[i]));
		}
		return cv;
	}

	/**
	 * 選考日程のComparator
	 *
	 * @author TC-0115
	 *
	 */
	public class SlcDateComparator implements Comparator<CandidatesView> {
		public int compare(CandidatesView cv1, CandidatesView cv2) {
			Integer cvStDtl1 = cv1.getSlcStatusDtlId();
			Integer cvStDtl2 = cv2.getSlcStatusDtlId();

			//並び替えのルール
			//一覧画面で選考日程を表示する選考ステータス詳細を先に並べる
			//一覧画面で選考日程を表示する選考ステータス詳細（選考中、承諾待ち、確定）同士の比較の場合、選考日程が早い順に並べる
			//一覧画面で選考日程を表示しない選考ステータス詳細同士の比較の場合、選考ステータス詳細の小さい順に並べる
			if (cvStDtl1 == SlcStatusDtlConstant.SELECTING || cvStDtl1 == SlcStatusDtlConstant.WATING_ACCEPTANCE
					|| cvStDtl1 == SlcStatusDtlConstant.CONFIRMED) {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING || cvStDtl2 == SlcStatusDtlConstant.WATING_ACCEPTANCE
						|| cvStDtl2 == SlcStatusDtlConstant.CONFIRMED) {
					return cv1.getSlcDate().compareTo(cv2.getSlcDate());
				} else {
					return -1;
				}
			} else {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING || cvStDtl2 == SlcStatusDtlConstant.WATING_ACCEPTANCE
						|| cvStDtl2 == SlcStatusDtlConstant.CONFIRMED) {
					return 1;
				} else {
					return Integer.compare(cvStDtl1, cvStDtl2);
				}
			}
		}
	}

}
