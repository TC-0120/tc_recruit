package jp.co.tc.recruit.entity.statistics;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.tc.recruit.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author TC-0120
 *統計設定マスタのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name = "XXTC_STATISTICS_CONFIG")
public class StatisticsConfig extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistics_config")
	@SequenceGenerator(name = "statistics_config", sequenceName = "xxtc_statistics_config_statistics_config_id_seq", allocationSize = 1)
	@Column(name="statistics_config_id")
	private Integer statisticsConfigId;

	@Column(name="statistics_type")
	private String statisticsType;

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
