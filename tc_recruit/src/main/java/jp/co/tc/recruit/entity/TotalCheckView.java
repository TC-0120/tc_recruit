package jp.co.tc.recruit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TOTAL_CHECK")
public class TotalCheckView {
	@Column(name="total_document")
	private Integer ttlDocument;

	@Column(name="total_first_adjust")
	private Integer ttlFirstAdjust;

	@Column(name="total_first_assessment")
	private Integer ttlFirstAssessment;

	@Column(name="total_second_adjust")
	private Integer ttlSecondAdjust;

	@Column(name="total_second_assessment")
	private Integer ttlSecondAssessment;

	@Column(name="total_last_adjust")
	private Integer ttlLastAdjust;

	@Column(name="total_last_assessment")
	private Integer ttlLastAssessment;

	@Column(name="total_briefing_adjust")
	private Integer ttlBriefingAdjust;

	@Column(name=" total_briefing_assessment")
	private Integer ttlBriefingAssessment;
}
