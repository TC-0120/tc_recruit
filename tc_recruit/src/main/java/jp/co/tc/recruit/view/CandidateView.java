package jp.co.tc.recruit.view;

import jp.co.tc.recruit.entity.Candidate;

public class CandidateView {
	private Candidate candidate;
	private String slcDate;

	public CandidateView() {
	}

	public CandidateView(Candidate candidate) {
		this.candidate = candidate;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getSlcDate() {
		return slcDate;
	}

	public void setSlcDate(String slcDate) {
		this.slcDate = slcDate;
	}

}