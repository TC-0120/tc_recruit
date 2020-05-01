package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.tc.recruit.view.CandidatesView;

/**
 * 候補者情報ビューのリポジトリ
 *
 * @author TC-0115
 *
 */
@Repository
public interface CandidatesViewRepository extends JpaRepository<CandidatesView, Integer>, CandidatesViewRepositoryCustom {
	public CandidatesView findByCandidateId(Integer cId);
}

