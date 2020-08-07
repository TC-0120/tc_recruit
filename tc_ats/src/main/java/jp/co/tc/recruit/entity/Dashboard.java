package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.tc.recruit.entity.selection.SelectionStatus;
import jp.co.tc.recruit.entity.selection.SelectionStatusDetail;
import lombok.Data;

@Data
@Entity
@Table(name = "XXTC_DASHBOARD")
public class Dashboard extends AbstractEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dashboard")
	@SequenceGenerator(name = "dashboard", sequenceName = "xxtc_dashboard_dashboard_id_seq", allocationSize = 1)
	@Column(name = "dashboard_id")
	private Integer dashboardId;

	@Column(name = "status_message")
	private String statusMessage;

	@ManyToOne
	@JoinColumn(name = "selection_status_id")
	private SelectionStatus slcStatus;

	@ManyToOne
	@JoinColumn(name = "selection_status_detail_id")
	private SelectionStatusDetail slcStatusDtl;

	@Column(name = "icon")
	private String icon;

	@Column(name = "status_message_procedure")
	private Integer statusMessageProcedure;

	@Column(name = "message_status_flag")
	private Integer messageStatusFlag;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}

}
