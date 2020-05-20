package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="XXTC_MESSAGE_STATUS")
public class MessageStatus {
	@Id
	@GeneratedValue
	@Column(name="status_message_id")
	private Integer statusMessageId;

	@Column(name="status_message")
	private String statusMessage;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_detail_id")
	private Integer selectionStatusDetailId;
}
