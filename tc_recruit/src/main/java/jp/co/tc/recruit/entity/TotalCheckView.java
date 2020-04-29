package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_CHECK")
public class TotalCheckView implements Serializable {
	@Id
	@Column(name = "message_id")
	private Integer messageId;

	private String message;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_name")
	private String selectionStatusName;

	@Column(name="selection_status_detail_id")
	private Integer selectionStatusDetailId;

	@Column(name="selection_status_detail_name")
	private String selectionStatusDetailName;

	@Column(name="selection_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date selectionDate;

	@Column(name = "total_except_assessment")
	private Integer totalExceptAssessment;

	@Column(name = "total_assessment")
	private Integer totalAssessment;
}
