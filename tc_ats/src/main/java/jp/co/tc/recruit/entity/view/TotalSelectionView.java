package jp.co.tc.recruit.entity.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_SELECTION")
public class TotalSelectionView implements Serializable{
	@Id
	@Column(name="status_message_id")
	private Integer statusMessageId;

	@Column(name="status_message")
	private String statusMessage;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_name")
	private String selectionStatusName;

	@Column(name="message_status_flag")
	private Integer messageStatusFlag;

	private String icon;

	private Integer count;

	private Integer sort;


}
