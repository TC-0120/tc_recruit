package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXTC_MESSAGE_STATUS")
public class MessageStatus {
	@Id
	@GeneratedValue
	@Column(name="status_message_id")
	private Integer sttMsgId;

	@Column(name="status_message")
	private String sttMsg;
}
