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
import jp.co.tc.recruit.entity.Candidate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author TC-0120
 *学歴情報テーブルのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name = "XXTC_EDUCATIONAL_BACKGROUND")
public class EducationalBackground extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "educational_background")
	@SequenceGenerator(name = "educational_background", sequenceName = "xxtc_educational_background_educational_background_id_seq", allocationSize = 1)
	@Column(name = "educational_background_id")
	private Integer educationalBackgroundId;

	@ManyToOne
	@JoinColumn(name = "university_id")
	private University university;

	@ManyToOne
	@JoinColumn(name = "faculty_id")
	private Faculty faculty;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="educationalBackground", cascade=CascadeType.ALL)
	private List<Candidate> candidate;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}
}
