package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_MESSAGE_STATUS")
public class MessageStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="status_message_id")
	private Integer statusMessageId;

	@Column(name="status_message")
	private String statusMessage;
}
