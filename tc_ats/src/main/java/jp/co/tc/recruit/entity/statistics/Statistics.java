package jp.co.tc.recruit.entity.statistics;

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

import jp.co.tc.recruit.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 統計設定テーブルのエンティティ
 * @author TC-0120
 *
 */
@Getter
@Setter
@Entity
@Table(name = "XXTC_STATISTICS")
public class Statistics extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistics")
	@SequenceGenerator(name = "statistics", sequenceName = "xxtc_statistics_statistics_id_seq", allocationSize = 1)
	@Column(name="statistics_id")
	private Integer statisticsId;

	@Column(name="statistics_name")
	private String statisticsName;

	@ManyToOne
	@JoinColumn(name = "statistics_config_id")
	private StatisticsConfig statisticsConfig;

	@ManyToOne
	@JoinColumn(name = "statistics_constraint_id")
	private StatisticsConstraint statisticsConstraint;

	@Column(name="statistics_term")
	private Integer statisticsTerm;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="university_rank_id")
	private Integer university_rank_id;

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
