package jp.co.tc.recruit.entity.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_CHECK")
public class TotalCheckView implements Serializable {
	@Id
	@Column(name = "status_message_id")
	private Integer statusMessageId;

	@Column(name = "status_message")
	private String statusMessage;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_name")
	private String selectionStatusName;

	@Column(name="selection_status_detail_id")
	private Integer selectionStatusDetailId;

	@Column(name="selection_status_detail_name")
	private String selectionStatusDetailName;

	@Column(name="message_status_flag")
	private Integer messageStatusFlag;

	private Integer sort;

	@Column(name = "total_count")
	private Integer totalCount;

	@Column(name = "total_over_date")
	private Integer totalOverDate;
}
