package jp.co.tc.recruit.entity.statistics;

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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author TC-0120
 *統計詳細条件マスタのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name = "XXTC_STATISTICS_CONSTRAINT")
public class StatisticsConstraint extends AbstractEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistics_constraint")
	@SequenceGenerator(name = "statistics_constraint", sequenceName = "xxtc_statistics_constraint_statistics_constraint_id_seq", allocationSize = 1)
	@Column(name="statistics_constraint_id")
	private Integer statisticsConstraintId;

	@Column(name="statistics_constraint")
	private String statisticsConstraint;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="statisticsConstraint", cascade=CascadeType.ALL)
	private List<Statistics> statistics;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}
}
