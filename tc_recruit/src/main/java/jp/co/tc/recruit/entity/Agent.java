package jp.co.tc.recruit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_AGENT")
public class Agent {

	@Id
	@GeneratedValue
	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="agent_name")
	private String agentName;

	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
	private List<Referrer> referrers;

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

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public List<Referrer> getReferrers() {
		return referrers;
	}

	public void setReferrers(List<Referrer> referrers) {
		this.referrers = referrers;
	}

}
