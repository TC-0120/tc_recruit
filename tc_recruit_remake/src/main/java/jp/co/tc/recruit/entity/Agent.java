package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 採用エージェントのエンティティ
 *
 * @author TC-0115
 *
 */
@Data
@Entity
@Table(name="XXTC_AGENT")
public class Agent implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="agent_name")
	private String agentName;

//	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
//	private List<Candidate> candidates;
//
//	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
//	private List<Referrer> referrers;

	@Column(name="referrer_fee")
	private String referrerFee;

}
