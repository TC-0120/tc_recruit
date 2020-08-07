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
 *大学ランク用エンティティ
 */
@Getter
@Setter
@Entity
@Table(name="XXTC_UNIVERSITY_RANK")
public class UniversityRank extends AbstractEntity implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "university_rank")
	@SequenceGenerator(name = "university_rank", sequenceName = "xxtc_university_rank_university_rank_id_seq", allocationSize = 1)
	@Column(name="university_rank_id")
	private Integer universityRankId;

	@Column(name="university_rank")
	private Integer universityRank;

	@Column(name="university_rank_name")
	private String universityRankName;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@OneToMany(mappedBy="universityRank", cascade=CascadeType.ALL)
	private List<University> university;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}
}
