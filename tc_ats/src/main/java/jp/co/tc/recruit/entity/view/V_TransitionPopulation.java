package jp.co.tc.recruit.entity.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TRANSITION_POPULATION")
public class V_TransitionPopulation implements Serializable   {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="candidate_id")
	private Integer candidateId;

	@Column(name="university_rank_id")
	private Integer universityRankId;

	@Column(name="educational_background_id")
	private Integer educationalBackgroundId;

	@Column(name="selection_status_id")
	private Integer selectionStatusId;

	@Column(name="selection_status_detail_id")
	private Integer selectionStatusDetailId;

	@Column(name="agent_id")
	private Integer agentId;

	@Column(name="aptitude_id")
	private Integer aptitudeId;

	@Column(name="insert_month")
	private Integer insertMonth;

	@Column(name="insert_year")
	private Integer insertYear;
}
