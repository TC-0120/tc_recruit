package jp.co.tc.recruit.entity.selection;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * @author TC-0120
 *選考参照マスタのエンティティ
 */
@Data
@Entity
@Table(name = "XXTC_SELECTION_REFERRER")
public class SelectionReferrer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selection_referrer")
	@SequenceGenerator(name = "selection_referrer", sequenceName = "xxtc_selection_referrer_selection_referrer_id_seq", allocationSize = 1)
	@Column(name = "selection_referrer_id")
	private Integer selectionReferrerId;

	@ManyToOne
	@JoinColumn(name = "selection_status_id")
	private SelectionStatus slcStatus;

	@ManyToOne
	@JoinColumn(name = "selection_status_detail_id")
	private SelectionStatusDetail slcStatusDtl;
}
