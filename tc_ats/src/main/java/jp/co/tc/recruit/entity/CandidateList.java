package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "XXTC_CANDIDATE_LIST")
public class CandidateList  extends AbstractEntity implements Serializable {

	@Id
	@Column(name="candidate_list_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_sequence")
	@SequenceGenerator(name = "candidate_sequence", sequenceName = "xxtc_candidate_list_candidate_list_id_seq", allocationSize = 1)
	private Integer candidateListId;

	@Column(name="candidate_id")
	private Integer candidateId;

	@Column(name="candidate_name")
	private Integer candidateName;

	@Column(name="candidate_name_furigana")
	private Integer candidateFurigana;

	@Column(name="gender")
	private Integer gender;

	@Column(name="educational_background")
	private Integer eduBack;

	@Column(name="candidate_email_address")
	private Integer candidateEmailAddress;

	@Column(name="selection_status_id")
	private Integer slcStatus;

	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtl;

	@Column(name="selection_date")
	private Integer selectionDate;

	@Column(name="agent_id")
	private Integer agent;

//	@ManyToOne
//	@JoinColumn(name="referrer_id")
//	private Referrer referrer;

	@Column(name="aptitude_id")
	private Integer aptitude;

	@Column(name="aptitude_score")
	private Integer aptitudeScore;

	@Column(name="statistics_info_id")
	private Integer statisticsInfoId;

	@Column(name="remarks")
	private Integer remarks;

	@Column(name = "insert_user_flag")
	private Integer insertUserFlag;

	@Column(name = "insert_date_flag")
	private Integer insertDateFlag;

	@Column(name = "update_user_flag")
	private Integer updateUserFlag;

	@Column(name = "update_date_flag")
	private Integer updateDateFlag;

	@Column(name = "delete_flag_flag")
	private Integer deleteFlagFlag;

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
