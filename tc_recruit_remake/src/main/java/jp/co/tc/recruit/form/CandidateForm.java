package jp.co.tc.recruit.form;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateForm implements Serializable{

	private List<Integer> candidateId;

	private List<String> candidateName;

	private List<String> candidateFurigana;

	private List<Integer> gender;

	private List<String> eduBack;

	private List<SelectionStatusForm> slcStatus;

	private List<SelectionStageForm> selectionStage;

	private List<AgentForm> agent;

	private List<String> remarks;

	private List<Integer> aptitudeFlag;

	private List<Integer> deleteFlag;

	private List<Integer> aptitudeScore;

	private List<String> insertDate;

	private List<Integer> sortNumber;

	private Integer sortType;

	private Integer sortCheck;

	public CandidateForm() {
		this.candidateId = null;
		this.candidateName = null;
		this.candidateFurigana = null;
		this.gender = null;
		this.eduBack = null;
		this.slcStatus = null;
		this.selectionStage = null;
		this.agent = null;
		this.remarks = null;
		this.aptitudeFlag = null;
		this.deleteFlag = null;
		this.aptitudeScore = null;
	}

	public List<Integer> getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(List<Integer> candidateId) {
		this.candidateId = candidateId;
	}

	public List<String> getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(List<String> candidateName) {
		this.candidateName = candidateName;
	}

	public List<String> getCandidateFurigana() {
		return candidateFurigana;
	}

	public void setCandidateFurigana(List<String> candidateFurigana) {
		this.candidateFurigana = candidateFurigana;
	}

	public List<Integer> getGender() {
		return gender;
	}

	public void setGender(List<Integer> gender) {
		this.gender = gender;
	}

	public List<String> getEduBack() {
		return eduBack;
	}

	public void setEduBack(List<String> eduBack) {
		this.eduBack = eduBack;
	}

	public List<SelectionStatusForm> getSlcStatus() {
		return slcStatus;
	}

	public void setSlcStatus(List<SelectionStatusForm> slcStatus) {
		this.slcStatus = slcStatus;
	}

	public List<SelectionStageForm> getSelectionStage() {
		return selectionStage;
	}

	public void setSelectionStage(List<SelectionStageForm> selectionStage) {
		this.selectionStage = selectionStage;
	}

	public List<AgentForm> getAgent() {
		return agent;
	}

	public void setAgent(List<AgentForm> agent) {
		this.agent = agent;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public List<Integer> getAptitudeFlag() {
		return aptitudeFlag;
	}

	public void setAptitudeFlag(List<Integer> aptitudeFlag) {
		this.aptitudeFlag = aptitudeFlag;
	}

	public List<Integer> getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(List<Integer> deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public List<Integer> getAptitudeScore() {
		return aptitudeScore;
	}

	public void setAptitudeScore(List<Integer> aptitudeScore) {
		this.aptitudeScore = aptitudeScore;
	}

}
