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
}
