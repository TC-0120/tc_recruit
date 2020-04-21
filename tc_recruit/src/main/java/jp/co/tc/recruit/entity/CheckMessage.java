package jp.co.tc.recruit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
public class CheckMessage {
	@Id
	@Column(name="message_id")
	private Integer messageId;

	private String message;

	@Column(name="selection_status_id")
	private Integer sltStatusId;

	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="def_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date defDate;

}
