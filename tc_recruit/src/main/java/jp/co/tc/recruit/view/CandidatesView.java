package jp.co.tc.recruit.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="VIEW_CANDIDATES")
public class CandidatesView {

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

	@Column(name="selection_status_id")
	private Integer slcStatusId;

	@Column(name="selection_status_name")
	private String slcStatusName;

	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="agent_name")
	private String agentName;

	@Column(name="referrer_id")
	private Integer referrerId;

	@Column(name="referrer_name")
	private String referrerName;

	@Column(name="selection_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date slcDate;

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

	public Integer getSlcStatusId() {
		return slcStatusId;
	}

	public void setSlcStatusId(Integer slcStatusId) {
		this.slcStatusId = slcStatusId;
	}

	public String getSlcStatusName() {
		return slcStatusName;
	}

	public void setSlcStatusName(String slcStatusName) {
		this.slcStatusName = slcStatusName;
	}

	public Integer getSlcStatusDtlId() {
		return slcStatusDtlId;
	}

	public void setSlcStatusDtlId(Integer slcStatusDtlId) {
		this.slcStatusDtlId = slcStatusDtlId;
	}

	public String getSlcStatusDtlName() {
		return slcStatusDtlName;
	}

	public void setSlcStatusDtlName(String slcStatusDtlName) {
		this.slcStatusDtlName = slcStatusDtlName;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public Date getSlcDate() {
		return slcDate;
	}

	public void setSlcDate(Date slcDate) {
		this.slcDate = slcDate;
	}
}
