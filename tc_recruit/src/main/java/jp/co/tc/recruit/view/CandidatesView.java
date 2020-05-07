package jp.co.tc.recruit.view;

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
public class CandidatesView {

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

	@Column(name="selection_status_id")
	private Integer slcStatusId;

	@Column(name="selection_status_name")
	private String slcStatusName;

	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="agent_name")
	private String agentName;

	@Column(name="referrer_id")
	private Integer referrerId;

	@Column(name="referrer_name")
	private String referrerName;

	@Column(name="selection_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date slcDate;

	public String getSlcDate() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(slcDate);
	}

}
