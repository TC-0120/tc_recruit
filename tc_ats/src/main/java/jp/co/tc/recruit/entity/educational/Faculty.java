package jp.co.tc.recruit.entity.educational;

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
 *学部マスタのエンティティ
 */
@Getter
@Setter
@Entity
@Table(name="XXTC_FACULTY")
public class Faculty  extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faculty")
	@SequenceGenerator(name = "faculty", sequenceName = "xxtc_faculty_faculty_id_seq", allocationSize = 1)
	@Column(name="faculty_id")
	private Integer facultyId;

	@Column(name="faculty_name")
	private String facultyName;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="faculty", cascade=CascadeType.ALL)
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
