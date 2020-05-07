package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;
import jp.co.tc.recruit.repository.SelectionStatusDetailRepository;
import jp.co.tc.recruit.repository.SelectionStatusRepository;

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

	public Candidate findById(Integer id) {
		return candidateRepo.findByCandidateId(id);
	}

<<<<<<< HEAD
	/*public List<Candidate> findBySlcStatusIdAndSlcStatudDtlId(Integer ssId, Integer ssdId) {
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

=======
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
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
<<<<<<< HEAD
	*/


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
=======
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
}
