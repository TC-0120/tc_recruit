package jp.co.tc.recruit.repository;

import java.util.List;

import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.view.CandidatesView;

public interface CandidatesViewRepositoryCustom {
	List<CandidatesView> findBySlcDate(String from, String to);

	List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlId(ConditionsForm cf);
}
