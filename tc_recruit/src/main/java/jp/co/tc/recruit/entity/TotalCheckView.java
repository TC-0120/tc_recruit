package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="TOTAL_CHECK")
public class TotalCheckView {
	@Id
	@Column(name = "message_id")
	private Integer messageId;

	private String message;

	private Integer count;
}
