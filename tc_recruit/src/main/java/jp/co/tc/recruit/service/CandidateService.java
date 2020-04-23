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
		candidateRepo.save(candidate);
	}

	public void update(Candidate candidate) {
		if ((candidate.getSlcStatus().getSlcStatusId() != 1 && candidate.getSlcStatusDtl().getSlcStatusDtlId() == 7)) {
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(1));
		}

		candidateRepo.save(candidate);
	}

	public void delete(Integer candidateId) {
		//candidateRepository.deleteById(candidateId);
		candidateRepo.deleteByCandidtaeId(candidateId);
	}

	//自動管理
	//マジックナンバー
	public void slcStatusManagement(Integer cId, Integer slcResult, String slcDate) {
		Candidate candidate = findById(cId);


		//変更前のステータス詳細が合格の場合、ステータスを繰り上げる
		//if (candidate.getSlcStatusDtl().getSlcStatusDtlId() == 3) {
		//	Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
		//	candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));
		//}

		if (slcResult == 0) {
			if (slcDate.isEmpty() || slcDate == null) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(1));
			} else {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(2));
			}
		} else {
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcResult));
		}

		candidateRepo.save(candidate);
	}

	public void SlcStatusUp(Integer cId) {
		Candidate candidate = findById(cId);
		Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();


		if (candidate.getSlcStatusDtl().getSlcStatusDtlId() == 3 && slcStatusId != 6) {
			candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(1));
		}

		candidateRepo.save(candidate);
	}
}
