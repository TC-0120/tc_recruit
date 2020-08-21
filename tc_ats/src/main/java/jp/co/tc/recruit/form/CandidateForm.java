package jp.co.tc.recruit.form;

import java.io.Serializable;

import jp.co.tc.recruit.entity.Agent;
import jp.co.tc.recruit.entity.Aptitude;
import jp.co.tc.recruit.entity.educational.EducationalBackground;
import jp.co.tc.recruit.entity.educational.UniversityRank;
import jp.co.tc.recruit.entity.selection.SelectionStatus;
import jp.co.tc.recruit.entity.selection.SelectionStatusDetail;
import lombok.Data;

@Data
public class CandidateForm implements Serializable{

	private Integer candidateId;

	public CandidateForm() {

	}

	private String candidateName;

	private String candidateFurigana;

	private Integer gender;

	private String eduBack;

	private UniversityRank universityRank;

	private EducationalBackground educationalBackground;

	private Integer eduBackCheck;

	private String candidateEmailAddress;

	private SelectionStatus slcStatus;

	private SelectionStatusDetail slcStatusDtl;

	private Agent agent;

	private Aptitude aptitude;

	private Integer aptitudeScore;

	private Integer statisticsInfoId;

	private String remarks;

	private Integer deleteFlag;

}
