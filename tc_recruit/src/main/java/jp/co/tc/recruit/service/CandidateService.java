package jp.co.tc.recruit.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;
import jp.co.tc.recruit.repository.SelectionStatusDetailRepository;
import jp.co.tc.recruit.repository.SelectionStatusRepository;
import jp.co.tc.recruit.view.CandidateView;

@Service
public class CandidateService {

	@Autowired
	CandidateRepository candidateRepo;
	@Autowired
	SelectionRepository slcRepo;
	@Autowired
	SelectionStatusRepository slcStatusRepo;
	@Autowired
	SelectionStatusDetailRepository slcStatusDtlRepo;

	public List<Candidate> findAll() {
		return candidateRepo.findAll(Sort.by("candidateId"));
	}

	public List<CandidateView> list(List<Candidate> cList) {
		List<Selection> sList = slcRepo.findAll();
		List<CandidateView> cvList = new ArrayList<CandidateView>();

		for(Candidate c : cList) {
			CandidateView cv = new CandidateView(c);

			switch(c.getSlcStatusDtl().getSlcStatusDtlId()) {
				case 1:
					cv.setSlcDate("調整中");
					break;
				case 2:
					for(Selection s: sList) {
						if (c.getCandidateId().equals(s.getSlcPK().getCandidateId()) &&
							c.getSlcStatus().getSlcStatusId().equals(s.getSlcPK().getSlcStatusId())) {
							cv.setSlcDate(new SimpleDateFormat("yyyy/MM/dd hh:mm").format(s.getSlcDate()));
						}
					}
					break;
				case 3:
					cv.setSlcDate("調整");
					break;
				default:
					cv.setSlcDate("");
					break;
			}

			cvList.add(cv);
		}

		return cvList;
	}





	public Candidate findById(Integer id) {
		return candidateRepo.findByCandidateId(id);
	}

	public List<Candidate> findBySlcStatusIdAndSlcStatudDtlId(Integer ssId, Integer ssdId) {
		List<Candidate> candidates = new ArrayList<Candidate>();

		if (ssdId == 0) {
			if (ssId == 0) {
				candidates = findAll();
			} else {
				candidates = slcStatusRepo.findBySlcStatusId(ssId).getCandidates();
			}
		} else if (ssdId == -1) {
				List<Candidate> c1 = slcStatusDtlRepo.findBySlcStatusDtlId(1).getCandidates();
				c1.addAll(slcStatusDtlRepo.findBySlcStatusDtlId(3).getCandidates());

			if (ssId == 0) {
				candidates = c1;
			} else {
				List<Candidate> c2 = slcStatusRepo.findBySlcStatusId(ssId).getCandidates();
				for(Candidate c : c1){
					if(c2.contains(c)){
						candidates.add(c);
					}
				}
			}
		} else {
			if (ssId == 0) {
				candidates = slcStatusDtlRepo.findBySlcStatusDtlId(ssdId).getCandidates();
			} else {
				List<Candidate> c1 = slcStatusRepo.findBySlcStatusId(ssId).getCandidates();
				List<Candidate> c2 = slcStatusDtlRepo.findBySlcStatusDtlId(ssdId).getCandidates();

				for(Candidate c : c1){
					if(c2.contains(c)){
						candidates.add(c);
					}
				}
			}
		}

		Collections.sort(candidates, new CandidateComparator());
		return candidates;
	}

	public Candidate save(Candidate candidate) {
		return candidateRepo.save(candidate);
	}

	public void delete(Integer candidateId) {
		//candidateRepository.deleteById(candidateId);
		candidateRepo.deleteByCandidtaeId(candidateId);
	}

	public void slcStatusManagement(Integer candidateId, Integer slcStatusDtlId) {
		Candidate candidate = findById(candidateId);

		//ステータス詳細が合格の場合、ステータスを繰り上げる
		if (candidate.getSlcStatusDtl().getSlcStatusDtlId() == 3) {
			Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
			candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));
		}

		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));
		save(candidate);
	}



	public List<CandidateView> sort(Integer order, Integer dir, List<CandidateView> cv) {

		if (order == 1) {
			Collections.sort(cv, new CandidateIdComparator());
		} else if (order == 2) {
			Collections.sort(cv, new SlcStatusComparator());
		} else if (order == 3) {
			Collections.sort(cv, new SlcStatusDtlComparator());
		} else {
			//Collections.sort(cv, new SlcDateComparator());
		}

		if(dir == 2) {
			Collections.reverse(cv);
		}
		return cv;
	}


	public class CandidateComparator implements Comparator<Candidate> {
		public int compare(Candidate c1, Candidate c2) {
			return Integer.compare(c1.getCandidateId(), c2.getCandidateId());
		}
	}

	public class CandidateIdComparator implements Comparator<CandidateView> {
		public int compare(CandidateView c1, CandidateView c2) {
			return Integer.compare(c1.getCandidate().getCandidateId(), c2.getCandidate().getCandidateId());
		}
	}


	public class SlcStatusComparator implements Comparator<CandidateView> {
		public int compare(CandidateView cv1, CandidateView cv2) {
			return Integer.compare(cv1.getCandidate().getSlcStatus().getSlcStatusId(), cv2.getCandidate().getSlcStatus().getSlcStatusId());
		}

	}

	public class SlcStatusDtlComparator implements Comparator<CandidateView> {
		public int compare(CandidateView cv1, CandidateView cv2) {
			return Integer.compare(cv1.getCandidate().getSlcStatusDtl().getSlcStatusDtlId(), cv2.getCandidate().getSlcStatusDtl().getSlcStatusDtlId());
		}
	}

	//public class SlcDateComparator implements Comparator<CandidateView> {
	//}
}
