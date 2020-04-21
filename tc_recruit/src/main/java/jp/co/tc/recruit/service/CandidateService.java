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

	public void save(Candidate candidate) {
		candidateRepo.save(candidate);
	}

	//マジックナンバー
	public void regist(Candidate candidate, String slcDate) {
		Integer slcStatusDtlId = null;

		if (candidate.getSlcStatus().getSlcStatusId() == 1) {
			slcStatusDtlId = 7;
		} else if (candidate.getSlcStatus().getSlcStatusId() == 2) {
			if (slcDate.isEmpty() || slcDate == null) {
				slcStatusDtlId = 1;
			} else {
				slcStatusDtlId = 2;
			}
		}
		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));
		save(candidate);
	}

	public void delete(Integer candidateId) {
		//candidateRepository.deleteById(candidateId);
		candidateRepo.deleteByCandidtaeId(candidateId);
	}

	//マジックナンバー
	//public void slcStatusManagement(Integer candidateId, Integer slcStatusDtlId) {
	//	Candidate candidate = findById(candidateId);
    //
	//	//ステータス詳細が合格の場合、ステータスを繰り上げる
	//	if (candidate.getSlcStatusDtl().getSlcStatusDtlId() == 3) {
	//		Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
	//		candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));
	//	}
    //
	//	candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));
	//	save(candidate);
	//}

	//自動管理
	//マジックナンバー
	public void slcStatusManagement(Integer candidateId, String slcDate, Integer slcResult) {
		Candidate candidate = findById(candidateId);
		Integer slcStatusDtlId = null;

		//変更前のステータス詳細が合格の場合、ステータスを繰り上げる
		if (candidate.getSlcStatusDtl().getSlcStatusDtlId() == 3) {
			Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
			candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));
		}

		if (slcDate.isEmpty() || slcDate == null) {
			slcStatusDtlId = 1;
		} else {
			slcStatusDtlId = 2;
		}

		if (slcResult != null) {
			if (slcResult == 0) {
				slcStatusDtlId = 4;
			} else if (slcResult == 1) {
				slcStatusDtlId = 3;
			}
		}

		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));
		save(candidate);
	}
}
