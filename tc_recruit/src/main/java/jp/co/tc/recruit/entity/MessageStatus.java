package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "XXTC_MESSAGE_STATUS")
public class MessageStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_status_sequence")
	@SequenceGenerator(name = "message_status_sequence", sequenceName = "message_status_sequence", allocationSize = 1)
	@Column(name = "status_message_id")
	private Integer statusMessageId;

	@Column(name = "status_message")
	private String statusMessage;

	@Column(name = "selection_status_id")
	private Integer selectionStatusId;

	@Column(name = "selection_status_name")
	private String selectionStatusName;

	@Column(name = "selection_status_detail_id")
	private Integer selectionStatusDetailId;

	@Column(name = "selection_status_detail_name")
	private String selectionStatusDetailName;

	private String icon;

	private Integer sort;

	@Column(name = "message_status_flag")
	private Integer messageStatusFlag;

}
