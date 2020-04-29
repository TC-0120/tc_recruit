package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_STATUS")
public class TotalStatusView implements Serializable{
	@Id
	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_name")
	private String selectionStatusName;

	private Integer count;


}
