package jp.co.tc.recruit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_REFERRER")
public class Referrer {

	@Id
	@GeneratedValue
	@Column(name="referrer_id")
	private Integer referrerId;

	@Column(name="referrer_name")
	private String referrerName;

	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

	public Integer getReferrerId() {
		return referrerId;
	}

	public void setReferrerId(Integer referrerId) {
		this.referrerId = referrerId;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setRefererName(String referrerName) {
		this.referrerName = referrerName;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}


}
