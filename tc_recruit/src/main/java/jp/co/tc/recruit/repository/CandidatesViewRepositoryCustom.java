package jp.co.tc.recruit.repository;

import java.util.List;

import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.view.CandidatesView;

/**
 * 候補者情報ビューリポジトリのカスタムメソッドを定義したインターフェイス
 *
 * @author TC-0115
 *
 */
public interface CandidatesViewRepositoryCustom {
	List<CandidatesView> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf);
}
