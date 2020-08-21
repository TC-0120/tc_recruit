package jp.co.tc.recruit.util;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.form.CandidateForm;
import jp.co.tc.recruit.repository.educational.EducationalBackgroundRepository;
import jp.co.tc.recruit.service.AbstractEntityService;

public class BeanCopy {

	@Autowired
	AbstractEntityService abstractEntityService;
	@Autowired
	EducationalBackgroundRepository educationalBackgroundRepository;

	//学歴を直接記入した場合
	private static final int NOT_EDUBACK_DATA = 1;


	public static Candidate copyFormToEntity(CandidateForm candidateForm) {

		Candidate candidate = new Candidate();
		candidate.setAgent(candidateForm.getAgent());
		candidate.setAptitude(candidateForm.getAptitude());
		candidate.setAptitudeScore(candidateForm.getAptitudeScore());
		candidate.setCandidateFurigana(candidateForm.getCandidateFurigana());
		candidate.setCandidateId(candidateForm.getCandidateId());
		candidate.setCandidateName(candidateForm.getCandidateName());
		candidate.setCandidateEmailAddress(candidateForm.getCandidateEmailAddress());
		candidate.setDeleteFlag(candidateForm.getDeleteFlag());
		candidate.setGender(candidateForm.getGender());
		candidate.setRemarks(candidateForm.getRemarks());
		candidate.setSlcStatus(candidateForm.getSlcStatus());
		candidate.setSlcStatusDtl(candidateForm.getSlcStatusDtl());
		System.out.println(candidate.getAgent().getAgentId());

		//入力形式に応じた学歴情報を設定
		AbstractEntityService abstractEntityService = new AbstractEntityService();
		if (candidateForm.getEduBackCheck() == NOT_EDUBACK_DATA) {
			//candidateForm.getEducationalBackground().setEducationalBackgroundId(NO_REFERRER_DATA);
			candidate.setEducationalBackground(null);
			candidate.setEduBack(candidateForm.getEduBack());
		} else {
			//abstractEntityService.setInsertUer(candidateForm.getEducationalBackground());
			candidate.setEducationalBackground(candidateForm.getEducationalBackground());
			candidate.setEduBack(candidateForm.getEduBack());
		}
		candidate.setUniversityRank(candidateForm.getUniversityRank());

		return candidate;
	}
}
