package jp.co.tc.recruit.repository;

import java.util.List;

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

	public List<CandidatesView> findBySlcStatusDtlIdOrSlcStatusDtlId(Integer ssdId1, Integer ssdId2);
}

