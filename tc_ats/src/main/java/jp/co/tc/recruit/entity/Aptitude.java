package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author TC-0120
 *適性検査マスタのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name = "XXTC_APTITUDE")
public class Aptitude extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aptitude")
	@SequenceGenerator(name = "aptitude", sequenceName = "xxtc_aptitude_aptitude_id_seq", allocationSize = 1)
	@Column(name="aptitude_id")
	private Integer aptitudeId;

	@Column(name="aptitude_status")
	private String aptitudeStatus;

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
