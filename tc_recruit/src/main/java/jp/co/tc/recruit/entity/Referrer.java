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

import lombok.Data;

/**
 * 紹介元のエンティティ
 *
 * @author TC-0115
 *
 */
@Data
@Entity
@Table(name="XXTC_REFERRER")
public class Referrer {

	@Id
	@GeneratedValue
	@Column(name="referrer_id")
	private Integer referrerId;

	@Column(name="referrer_name")
	private String referrerName;

	@Column(name="referrer_fee")
	private Integer referrerFee;

	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

	@OneToMany(mappedBy="agent", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

}
