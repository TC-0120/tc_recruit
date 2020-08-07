package jp.co.tc.recruit.entity.educational;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 *大学マスタのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name="XXTC_UNIVERSITY")
public class University extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "university")
	@SequenceGenerator(name = "university", sequenceName = "xxtc_university_university_id_seq", allocationSize = 1)
	@Column(name="university_id")
	private Integer universityId;

	@Column(name="university_name")
	private String universityName;

	@ManyToOne
	@JoinColumn(name = "university_rank_id")
	private UniversityRank universityRank;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="university", cascade=CascadeType.ALL)
	private List<EducationalBackground> educationalBackground;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}
}
