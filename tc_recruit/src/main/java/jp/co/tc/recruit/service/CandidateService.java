package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.form.MultipleUpdateForm;
import jp.co.tc.recruit.repository.AgentRepository;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.ReferrerRepository;
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
	@Autowired
	AgentRepository agentRepo;
	@Autowired
	ReferrerRepository referrerRepo;

	public Candidate findById(Integer id) {
		return candidateRepo.findByCandidateId(id);
	}

	public void regist(Candidate candidate, String slcDate) {
		Integer slcStatusDtlId = null;

		if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.INFORMATION_SESSION) {
			slcStatusDtlId = SlcStatusDtlConstant.BLANK;
		} else if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.APPLICANT_SCREENING) {
			if (slcDate.isEmpty() || slcDate == null) {
				slcStatusDtlId = SlcStatusDtlConstant.ADJUSTING;
			} else {
				slcStatusDtlId = SlcStatusDtlConstant.SELECTING;
			}
		}
		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));
		candidateRepo.save(candidate);
	}

	public void update(Candidate candidate) {
		if ((candidate.getSlcStatus().getSlcStatusId() != SlcStatusConstant.INFORMATION_SESSION
				&& candidate.getSlcStatusDtl().getSlcStatusDtlId() == SlcStatusDtlConstant.BLANK)) {
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(1));
		}

		candidateRepo.save(candidate);
	}

	public void delete(Integer candidateId) {
		candidateRepo.deleteById(candidateId);
	}

	public void slcStatusManagement(Integer cId, Integer slcResult, String slcDate) {
		Candidate candidate = findById(cId);

		if (slcResult == SlcStatusDtlConstant.PENDING) {
			if (slcDate.isEmpty() || slcDate == null) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
			} else {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.SELECTING));
			}
		} else {
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcResult));
		}

		if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.JOINNING_PROCEDURE) {
			if (slcDate.isEmpty() || slcDate == null) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
			} else {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.CONFIRMED));
			}
		}

		candidateRepo.save(candidate);
	}

	public boolean slcStatusUp(Integer cId) {
		Candidate candidate = findById(cId);
		Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
		Integer slcStatusDtlId = candidate.getSlcStatusDtl().getSlcStatusDtlId();

		if (slcStatusDtlId == SlcStatusDtlConstant.CONFIRMED) {
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.FINISHED));
			candidateRepo.save(candidate);
			return false;
		}

		if (slcStatusDtlId == SlcStatusDtlConstant.PASSING
				|| slcStatusDtlId == SlcStatusDtlConstant.ACCEPTANCE) {
			candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(slcStatusId + 1));

			if (slcStatusId < SlcStatusConstant.LAST_INTERVIEW) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
			} else if(slcStatusId == SlcStatusConstant.LAST_INTERVIEW) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.WATING_ACCEPTANCE));
			} else if(slcStatusId == SlcStatusConstant.INFORMAL_OFFER) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
			}

		}

		candidateRepo.save(candidate);
		return true;
	}

	public List<Candidate> multipleUpdate(Integer[] cId, MultipleUpdateForm muForm) {
		List<Candidate> cList = new ArrayList<Candidate>();

		for (int i = 0; i < cId.length; i++) {
			Candidate candidate = findById(cId[i]);

			if (muForm.getGender() != null) {
				candidate.setGender(muForm.getGender());
			}

			if (!muForm.getEduBack().isEmpty()) {
				candidate.setEduBack(muForm.getEduBack());
			}

			if (muForm.getSlcStatusId() != 0) {
				candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(muForm.getSlcStatusId()));
			}

			if (muForm.getSlcStatusDtlId() != 0) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(muForm.getSlcStatusDtlId()));
			}

			if (muForm.getAgentId() != 0) {
				candidate.setAgent(agentRepo.findByAgentId(muForm.getAgentId()));
			}

			if (muForm.getReferrerId() != 0) {
				candidate.setReferrer(referrerRepo.findByReferrerId(muForm.getReferrerId()));
			}

			cList.add(candidate);
		}

		candidateRepo.saveAll(cList);
		return cList;
	}

	public void multipleDelete(Integer[] cId) {
		for (int i = 0; i < cId.length; i++) {
			candidateRepo.deleteById(cId[i]);
		}
	}
}
