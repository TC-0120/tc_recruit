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
		//Integer ssId = cf.getSlcStatusId();
		//Integer ssdId = cf.getSlcStatusDtlId();
		//Integer order = cf.getOrder();
		//Integer dir = cf.getDirection();
		//String from = cf.getFrom();
		//String to = cf.getTo();

		cv = repo.findBySlcStatusIdAndSlcStatudDtlId(cf);

		if (cf.getOrder() == ConditionsForm.SLC_DATE) {
			Collections.sort(cv, new SlcDateComparator());

			if (cf.getOrder() == ConditionsForm.DESC) {
				Collections.reverse(cv);
			}
		}


		//if (ssdId == 0) {
		//	if (ssId == 0) {
		//		cv = repo.findAll();
		//	} else {
		//		cv = repo.findBySlcStatusId(ssId);
		//	}
		//} else if (ssdId == -1) {
		//	if (ssId == 0) {
		//		cv = repo.findBySlcStatusDtlIdOrSlcStatusDtlId(1, 3);
		//	} else {
		//		cv = repo.findBySlcStatusIdAndSlcStatusDtlIdOrSlcStatusDtlId(ssId, 1, 3);
		//	}
		//} else {
		//	if (ssId == 0) {
		//		cv = repo.findBySlcStatusDtlId(ssdId);
		//	} else {
		//		cv = repo.findBySlcStatusIdAndSlcStatusDtlId(ssId, ssdId);
		//	}
		//}
        //
		//if (!from.isEmpty() || !to.isEmpty()) {
		//cv = repo.findBySlcDate(from, to);
		//}
        //
		//if (order == 2) {
		//	Collections.sort(cv, new SlcStatusComparator());
		//} else if (order == 3) {
		//	Collections.sort(cv, new SlcStatusDtlComparator());
		//} else if (order == 4) {
		//	Collections.sort(cv, new SlcDateComparator());
		//}
        //
		//if(dir == 2) {
		//	Collections.reverse(cv);
		//}
        //
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
