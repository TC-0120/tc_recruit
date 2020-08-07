package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 採用エージェントのエンティティ
 *
 * @author TC-0115
 *
 */

@Getter
@Setter
@Entity
@Table(name = "XXTC_AGENT")
public class Agent extends AbstractEntity implements Serializable{

	@Id
	@Column(name = "agent_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agent_sequence")
	@SequenceGenerator(name = "agent_sequence", sequenceName = "xxtc_agent_agent_id_seq", allocationSize = 1)
	private Integer agentId;

	@Column(name = "agent_name")
	private String agentName;

	@Column(name = "agent_fee")
	private Integer agentFee;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
	private List<Candidate> candidates;

	@OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
	private List<Referrer> referrers;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersist() {
		//削除フラグを指定
		deleteFlag = 0;
	}

}
