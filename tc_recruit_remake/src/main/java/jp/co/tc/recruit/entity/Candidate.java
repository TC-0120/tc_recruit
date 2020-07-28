package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
	@Column(name="candidate_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_gen")
	@SequenceGenerator(name = "candidate_gen", sequenceName = "candidate_sequence", allocationSize = 1)
	private Integer candidateId;

	@Column(name="candidate_name")
	private String candidateName;

	@Column(name="candidate_name_furigana")
	private String candidateFurigana;

	@Column(name="gender")
	private Integer gender;

	@Column(name="educational_background")
	private String eduBack;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="selection_status_id")
	private SelectionStatus slcStatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "selection_stage_id" )
	private SelectionStage selectionStage;

	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

//	@ManyToOne
//	@JoinColumn(name="referrer_id")
//	private Referrer referrer;

	@Column(name="remarks")
	private String remarks;

	@Column(name="aptitude_flag")
	private Integer aptitudeFlag;

	@Column(name="delete_flag")
	private Integer deleteFlag;

	@Column(name="aptitude_score")
	private Integer aptitudeScore;

	@Column(name="insert_date")
	private Date insertDate;

}
