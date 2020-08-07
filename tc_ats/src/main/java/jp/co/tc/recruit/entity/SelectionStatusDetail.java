package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * 選考ステータス詳細のエンティティ
 *
 * @author TC-0115
 *
 */
@Data
@Entity
@Table(name="XXTC_SELECTION_STATUS_DETAIL")
public class SelectionStatusDetail implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="selection_status_flag")
	private Integer slcStatusFlag;

	@OneToMany(mappedBy="slcStatusDtl", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

}
