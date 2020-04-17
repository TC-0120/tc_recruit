package jp.co.tc.recruit.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
@Entity
@Table(name = "TOTAL_STATUS")
public class TotalStatusView{
	@Id
	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	private Integer count;
}
