package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_CANDIDATE")
public class Candidate implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="candidate_id")
	private Integer candidateId;

	@Column(name="candidate_name")
	private String candidateName;

	@Column(name="gender")
	private Integer gender;

	@Column(name="educational_background")
	private String eduBack;

	@ManyToOne
	@JoinColumn(name="selection_status_id")
	private SelectionStatus slcStatus;

	@ManyToOne
	@JoinColumn(name="selection_status_detail_id")
	private SelectionStatusDetail slcStatusDtl;

	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

	@ManyToOne
	@JoinColumn(name="referrer_id")
	private Referrer referrer;

	@Column(name="remarks")
	private String remarks;

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


}
