package jp.co.tc.recruit.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectionStageForm implements Serializable{

	private Integer selectionStageId;

	private String documentSelection;

	private Integer documentTotalization;

	private Integer documentSelectionResult;

	private String primaryInterview;

	private Integer primaryInterviewtTotalization;

	private Integer primaryInterviewResult;

	private String secondInterview;

	private Integer secondInterviewTotalization;

	private Integer secondInterviewResult;

	private String finalInterview;

	private Integer finalInterviewTotalization;

	private Integer finalInterviewResult;

	private String unofficialOffer;

	private Integer unofficialOfferTotalization;

	private Integer unofficialOfferResult;


}
