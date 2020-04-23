package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_STATUS")
public class TotalStatusView{
	@Id
	@Column(name="selection_status_id")
	private Integer slcSttId;

	private Integer count;
}
