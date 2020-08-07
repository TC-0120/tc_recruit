package jp.co.tc.recruit.util;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.form.CandidateForm;

public class BeanCopy {

	public static Candidate copyFormToEntuty(CandidateForm candidateForm) {

		Candidate candidate = new Candidate();
		candidate.setAgent(candidateForm.getAgent());
		//candidate.setAptitudeFlag(candidateForm.getAptitudeFlag());
		candidate.setAptitudeScore(candidateForm.getAptitudeScore());
		candidate.setCandidateFurigana(candidateForm.getCandidateFurigana());
		candidate.setCandidateId(candidateForm.getCandidateId());
		candidate.setCandidateName(candidateForm.getCandidateName());
		candidate.setEduBack(candidateForm.getEduBack());
		candidate.setGender(candidateForm.getGender());
		//candidate.setReferrer(candidateForm.getReferrer());
		candidate.setRemarks(candidateForm.getRemarks());
		candidate.setSlcStatus(candidateForm.getSlcStatus());
		candidate.setSlcStatusDtl(candidateForm.getSlcStatusDtl());

		return candidate;
	}
}
