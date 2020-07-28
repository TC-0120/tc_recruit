package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

/**
 * 選考段階のエンティティ
 * @author TC-0120
 *
 */
@Data
@Entity
@Table(name = "XXTC_SEKLECTION_STAGE")
public class SelectionStage implements Serializable {

	@Id
	@Column(name = "selection_stage_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selection_stage_gen")
	@SequenceGenerator(name = "selection_stage_gen", sequenceName = "selection_stage_sequence", allocationSize = 1)
	private Integer selectionStageId;

	@Column(name = "document_selection", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date documentSelection;

	@Column(name = "document_totalization")
	private Integer documentTotalization;

	@Column(name = "document_selection_result")
	private Integer documentSelectionResult;

	@Column(name = "primary_interview", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date primaryInterview;

	@Column(name = "primary_interviewt_totalization")
	private Integer primaryInterviewtTotalization;

	@Column(name = "primary_interview_result")
	private Integer primaryInterviewResult;

	@Column(name = "second_interview", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date secondInterview;

	@Column(name = "second_interview_totalization")
	private Integer secondInterviewTotalization;

	@Column(name = "second_interview_result")
	private Integer secondInterviewResult;

	@Column(name = "final_interview", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date finalInterview;

	@Column(name = "final_interview_totalization")
	private Integer finalInterviewTotalization;

	@Column(name = "final_interview_result")
	private Integer finalInterviewResult;

	@Column(name = "unofficial_offer", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date unofficialOffer;

	@Column(name = "unofficial_offer_totalization")
	private Integer unofficialOfferTotalization;

	@Column(name = "unofficial_offer_result")
	private Integer unofficialOfferResult;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "selectionStage")
	private List<Candidate> candidateList;

	//	@Id
	//	@Column(name = "selection_stage_id")
	//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selection_stage_sequence")
	//	@SequenceGenerator(name = "selection_stage_sequence", sequenceName = "selection_stage_sequence", allocationSize = 1)
	//	private Integer selectionStageId;
	//
	//	@Column(name = "document_selection", nullable = true)
	//	@Temporal(TemporalType.TIMESTAMP)
	//	private Date documentSelection;
	//
	//	@Column(name = "document_totalization")
	//	private Integer documentTotalization;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "selection_result_id", referencedColumnName = "selectionResultId")
	//	private SelectionResult documentSelectionResult;
	//
	//	@Column(name = "primary_interview", nullable = true)
	//	@Temporal(TemporalType.TIMESTAMP)
	//	private Date primaryInterview;
	//
	//	@Column(name = "primary_interviewt_totalization")
	//	private Integer primaryInterviewtTotalization;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "primary_interview_result", referencedColumnName = "selectionResultId")
	//	private SelectionResult primaryInterviewResult;
	//
	//	@Column(name = "second_interview", nullable = true)
	//	@Temporal(TemporalType.TIMESTAMP)
	//	private Date secondInterview;
	//
	//	@Column(name = "second_interview_totalization")
	//	private Integer secondInterviewTotalization;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "second_interview_result", referencedColumnName = "selectionResultId")
	//	private SelectionResult secondInterviewResult;
	//
	//	@Column(name = "final_interview", nullable = true)
	//	@Temporal(TemporalType.TIMESTAMP)
	//	private Date finalInterview;
	//
	//	@Column(name = "final_interview_totalization")
	//	private Integer finalInterviewTotalization;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "final_interview_result", referencedColumnName = "selectionResultId")
	//	private SelectionResult finalInterviewResult;
	//
	//	@Column(name = "unofficial_offer", nullable = true)
	//	@Temporal(TemporalType.TIMESTAMP)
	//	private Date unofficialOffer;
	//
	//	@Column(name = "unofficial_offer_totalization")
	//	private Integer unofficialOfferTotalization;
	//
	//	@ManyToOne
	//	@JoinColumn(name = "unofficial_offer_result", referencedColumnName = "selectionResultId")
	//	private SelectionResult unofficialOfferResult;
}
