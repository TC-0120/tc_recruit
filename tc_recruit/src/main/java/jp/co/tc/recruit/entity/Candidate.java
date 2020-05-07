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

import lombok.Data;

/**
 * 候補者情報のエンティティ
 *
 * @author TC-0115
 *
 */
@Data
@Entity
@Table(name="XXTC_CANDIDATE")
public class Candidate implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="candidate_id")
	private Integer candidateId;

	@Column(name="candidate_name")
	private String candidateName;

	@Column(name="candidate_name_furigana")
	private String candidateFurigana;

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

	@Column(name="aptitude_flag")
	private Integer aptitudeFlag;

	@Column(name="aptitude_score")
	private Integer aptitudeScore;

}
