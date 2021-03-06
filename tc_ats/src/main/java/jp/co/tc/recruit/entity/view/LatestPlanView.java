package jp.co.tc.recruit.entity.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "LATEST_PLAN")
public class LatestPlanView implements Serializable {
	@Id
	@Column(name = "candidate_id")
	private Integer candidateId;

	@Column(name = "candidate_name")
	private String candidateName;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_name")
	private String selectionStatusName;

	@Column(name="selection_status_detail_id")
	private Integer selectionStatusDetailId;

	@Column(name="selection_status_detail_name")
	private String selectionStatusDetailName;

	@Column(name="selection_date", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date selectionDate;

	public String getSelectionDate() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(selectionDate);
	}
}
