package jp.co.tc.recruit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 *選考結果のエンティティ
 * @author TC-0120
 *
 */
@Data
@Entity
@Table(name = "XXTC_SELECTION_RESULT")
public class SelectionResult implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "selection_result_id")
	private Integer selectionResultId;

	@Column(name = "selection_result")
	private String selectionResult;

	//	@OneToMany(mappedBy = "documentSelectionResult", cascade = CascadeType.ALL)
	//	private List<SelectionStage> documentSelectionStage;
	//
	//	@OneToMany(mappedBy = "primaryInterviewResult", cascade = CascadeType.ALL)
	//	private List<SelectionStage> primaryInterviewStage;
	//
	//	@OneToMany(mappedBy = "secondInterviewResult", cascade = CascadeType.ALL)
	//	private List<SelectionStage> secondInterviewStage;
	//
	//	@OneToMany(mappedBy = "finalInterviewResult", cascade = CascadeType.ALL)
	//	private List<SelectionStage> finalInterviewStage;
	//
	//	@OneToMany(mappedBy = "unofficialOfferResult", cascade = CascadeType.ALL)
	//	private List<SelectionStage> unofficialOfferStage;
}
