package jp.co.tc.recruit.entity.selection;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.tc.recruit.entity.AbstractEntity;
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Dashboard;
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
public class SelectionStatusDetail extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selection_status_detail")
	@SequenceGenerator(name = "selection_status_detail", sequenceName = "xxtc_selection_status_detail_selection_status_detail_id_seq", allocationSize = 1)
	@Column(name="selection_status_detail_id")
	private Integer slcStatusDtlId;

	@Column(name="selection_status_detail_name")
	private String slcStatusDtlName;

	@Column(name="selection_status_flag")
	private Integer slcStatusFlag;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="slcStatusDtl", cascade=CascadeType.ALL)
	private List<Candidate> candidates;

	@OneToMany(mappedBy="slcStatusDtl", cascade=CascadeType.ALL)
	private List<SelectionReferrer> selectionReferrer;

	@OneToMany(mappedBy="slcStatusDtl", cascade=CascadeType.ALL)
	private List<Dashboard> dashboard;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}

}
