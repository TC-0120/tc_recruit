package jp.co.tc.recruit.entity.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import jp.co.tc.recruit.entity.User;
import lombok.Data;

/**
 * 候補者情報ビューのエンティティ
 *
 * @author TC-0115
 *
 */
@Data
@Entity
@Table(name="VIEW_CANDIDATES")
public class V_Candidates {

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

	@Column(name="educational_background_id")
	private Integer eduBackId;

	@Column(name="candidate_email_address")
	private String candidateEmailAddress;

	@Column(name="aptitude_score")
	private Integer aptitudeScore;

	@Column(name="statistics_info_id")
	private Integer statisticsInfoId;

	@Column(name="remarks")
	private String remarks;

	@Column(name="insert_user")
	private Integer insertUser;

	@Transient
	private User insertUserData;

	@Column(name="insert_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate;

	@Column(name="update_user")
	private Integer updateUser;

	@Transient
	private User updateUserData;

	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name="delete_flag")
	private Integer deleteFlag;

	@Column(name="university_name")
	private String universityName;

	@Column(name="faculty_name")
	private String facultyName;

	@Column(name="department_name")
	private String departmentName;

	@Column(name="selection_status_id")
	private Integer slcStatusId;

	@Column(name="selection_status_name")
	private String slcStatusName;

	@Column(name="selection_procedure")
	private Integer selectionProcedure;

	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="agent_name")
	private String agentName;

	@Column(name="agent_fee")
	private Integer agentFee;

	@Column(name="aptitude_id")
	private Integer aptitudeId;

	@Column(name="aptitude_status")
	private String aptitudeStatus;

	@Column(name="selection_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date slcDate;

	public String getInsertDate() {
		if (insertDate == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(insertDate);
	}

	public String getUpdateDate() {
		if (updateDate == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(updateDate);
	}

	public String getSlcDate() {
		if (slcDate == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(slcDate);
	}

	/*public String setSlcDate() {
		if (slcDate == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(slcDate);
	}*/

}