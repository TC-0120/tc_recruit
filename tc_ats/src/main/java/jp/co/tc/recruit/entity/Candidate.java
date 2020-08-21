package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.tc.recruit.entity.educational.EducationalBackground;
import jp.co.tc.recruit.entity.educational.UniversityRank;
import jp.co.tc.recruit.entity.selection.SelectionStatus;
import jp.co.tc.recruit.entity.selection.SelectionStatusDetail;
import lombok.Getter;
import lombok.Setter;

/**
 * 候補者情報のエンティティ
 *
 * @author TC-0115
 *
 */
@Getter
@Setter
@Entity
@Table(name="XXTC_CANDIDATE")
public class Candidate extends AbstractEntity implements Serializable  {

	@Id
	@Column(name="candidate_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_sequence")
	@SequenceGenerator(name = "candidate_sequence", sequenceName = "xxtc_candidate_candidate_id_seq", allocationSize = 1)
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
	@JoinColumn(name = "university_rank_id")
	private UniversityRank universityRank;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="educational_background_id")
	private EducationalBackground educationalBackground;

	@Column(name="candidate_email_address")
	private String candidateEmailAddress;

	@ManyToOne
	@JoinColumn(name="selection_status_id")
	private SelectionStatus slcStatus;

	@ManyToOne
	@JoinColumn(name="selection_status_detail_id")
	private SelectionStatusDetail slcStatusDtl;

	@ManyToOne
	@JoinColumn(name="agent_id")
	private Agent agent;

//	@ManyToOne
//	@JoinColumn(name="referrer_id")
//	private Referrer referrer;

	@ManyToOne
	@JoinColumn(name="aptitude_id")
	private Aptitude aptitude;

	@Column(name="aptitude_score")
	private Integer aptitudeScore;

	@Column(name="statistics_info_id")
	private Integer statisticsInfoId;

	@Column(name="remarks")
	private String remarks;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}

}
