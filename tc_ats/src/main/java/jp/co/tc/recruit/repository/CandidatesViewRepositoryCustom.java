package jp.co.tc.recruit.repository;

import java.util.List;

import jp.co.tc.recruit.entity.view.V_Candidates;
import jp.co.tc.recruit.form.ConditionsForm;

/**
 * 候補者情報ビューリポジトリのカスタムメソッドを定義したインターフェイス
 *
 * @author TC-0115
 *
 */
public interface CandidatesViewRepositoryCustom {
	List<V_Candidates> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf);
}
