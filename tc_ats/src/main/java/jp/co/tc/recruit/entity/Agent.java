package jp.co.tc.recruit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

}
