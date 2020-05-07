package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlId(ConditionsForm cf) {
		List<CandidatesView> cv = new ArrayList<CandidatesView>();
		Integer ssId = cf.getSlcStatusId();
		Integer ssdId = cf.getSlcStatusDtlId();
		Integer order = cf.getOrder();
		Integer dir = cf.getDirection();


		if (ssdId == 0) {
			if (ssId == 0) {
				cv = repo.findAll();
			} else {
				cv = repo.findBySlcStatusId(ssId);
			}
		} else if (ssdId == -1) {
			if (ssId == 0) {
				cv = repo.findBySlcStatusDtlIdOrSlcStatusDtlId(1, 3);
			} else {
				cv = repo.findBySlcStatusIdAndSlcStatusDtlIdOrSlcStatusDtlId(ssId, 1, 3);
			}
		} else {
			if (ssId == 0) {
				cv = repo.findBySlcStatusDtlId(ssdId);
			} else {
				cv = repo.findBySlcStatusIdAndSlcStatusDtlId(ssId, ssdId);
			}
		}

		if (order == 1) {

		} else if (order == 2) {
			Collections.sort(cv, new SlcStatusComparator());
		} else if (order == 3) {
			Collections.sort(cv, new SlcStatusDtlComparator());
		} else {
			Collections.sort(cv, new SlcDateComparator());
		}

		if(dir == 2) {
			Collections.reverse(cv);
		}
		return cv;
	}

	public class SlcStatusComparator implements Comparator<CandidatesView> {
		public int compare(CandidatesView cv1, CandidatesView cv2) {
			return Integer.compare(cv1.getSlcStatusId(), cv2.getSlcStatusId());
		}

	}

	public class SlcStatusDtlComparator implements Comparator<CandidatesView> {
		public int compare(CandidatesView cv1, CandidatesView cv2) {
			return Integer.compare(cv1.getSlcStatusDtlId(), cv2.getSlcStatusDtlId());
		}
	}

	public class SlcDateComparator implements Comparator<CandidatesView> {
		public int compare(CandidatesView cv1, CandidatesView cv2) {
			Integer cvStDtl1 = cv1.getSlcStatusDtlId();
			Integer cvStDtl2 = cv2.getSlcStatusDtlId();

			if (cvStDtl1 == 2) {
				if (cvStDtl2 == 2) {
					return cv1.getSlcDate().compareTo(cv2.getSlcDate());
				} else {
					return -1;
				}
			} else {
				if (cvStDtl2 == 2) {
					return 1;
				} else {
					return Integer.compare(cvStDtl1, cvStDtl2);
				}
			}
		}
	}

}
