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

@Service
public class CandidatesViewService {

	@Autowired
	CandidatesViewRepository repo;

	public List<CandidatesView> findAll() {
		return repo.findAll();
	}

	//マジックナンバー
	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlId(ConditionsForm cf) {
		List<CandidatesView> cv = new ArrayList<CandidatesView>();

		cv = repo.findBySlcStatusIdAndSlcStatudDtlId(cf);

		if (cf.getOrder() == ConditionsForm.SLC_DATE) {
			Collections.sort(cv, new SlcDateComparator());

			if (cf.getOrder() == ConditionsForm.DESC) {
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

	public class SlcDateComparator implements Comparator<CandidatesView> {
		public int compare(CandidatesView cv1, CandidatesView cv2) {
			Integer cvStDtl1 = cv1.getSlcStatusDtlId();
			Integer cvStDtl2 = cv2.getSlcStatusDtlId();

			if (cvStDtl1 == SlcStatusDtlConstant.SELECTING) {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING) {
					return cv1.getSlcDate().compareTo(cv2.getSlcDate());
				} else {
					return -1;
				}
			} else {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING) {
					return 1;
				} else {
					return Integer.compare(cvStDtl1, cvStDtl2);
				}
			}
		}
	}

}
