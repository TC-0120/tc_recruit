package jp.co.tc.recruit.form;

import java.io.Serializable;

public class AgentForm implements Serializable{

	private Integer agentId;

	private String agentName;

	private String referrerFee;

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

	public String getReferrerFee() {
		return referrerFee;
	}

	public void setReferrerFee(String referrerFee) {
		this.referrerFee = referrerFee;
	}

}
