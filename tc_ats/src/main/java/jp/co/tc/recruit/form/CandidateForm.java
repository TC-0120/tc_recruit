package jp.co.tc.recruit.form;

import java.io.Serializable;

import jp.co.tc.recruit.entity.Agent;
import jp.co.tc.recruit.entity.Referrer;
import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.entity.SelectionStatusDetail;

public class CandidateForm implements Serializable{

	private Integer candidateId;

	public CandidateForm() {
		this.candidateId = null;
		this.candidateName = null;
		this.candidateFurigana = null;
		this.gender = null;
		this.eduBack = null;
		this.slcStatus = null;
		this.slcStatusDtl = null;
		this.agent = null;
		this.referrer = null;
		this.remarks = null;
		this.aptitudeFlag = null;
		this.aptitudeScore = null;
	}

	private String candidateName;

	private String candidateFurigana;

	private Integer gender;

	private String eduBack;

	private SelectionStatus slcStatus;

	private SelectionStatusDetail slcStatusDtl;

	private Agent agent;

	private Referrer referrer;

	private String remarks;

	private Integer aptitudeFlag;

	private Integer aptitudeScore;

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateFurigana() {
		return candidateFurigana;
	}

	public void setCandidateFurigana(String candidateFurigana) {
		this.candidateFurigana = candidateFurigana;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEduBack() {
		return eduBack;
	}

	public void setEduBack(String eduBack) {
		this.eduBack = eduBack;
	}

	public SelectionStatus getSlcStatus() {
		return slcStatus;
	}

	public void setSlcStatus(SelectionStatus slcStatus) {
		this.slcStatus = slcStatus;
	}

	public SelectionStatusDetail getSlcStatusDtl() {
		return slcStatusDtl;
	}

	public void setSlcStatusDtl(SelectionStatusDetail slcStatusDtl) {
		this.slcStatusDtl = slcStatusDtl;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Referrer getReferrer() {
		return referrer;
	}

	public void setReferrer(Referrer referrer) {
		this.referrer = referrer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getAptitudeFlag() {
		return aptitudeFlag;
	}

	public void setAptitudeFlag(Integer aptitudeFlag) {
		this.aptitudeFlag = aptitudeFlag;
	}

	public Integer getAptitudeScore() {
		return aptitudeScore;
	}

	public void setAptitudeScore(Integer aptitudeScore) {
		this.aptitudeScore = aptitudeScore;
	}

}
