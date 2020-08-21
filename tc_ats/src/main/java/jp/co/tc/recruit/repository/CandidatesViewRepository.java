package jp.co.tc.recruit.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.tc.recruit.entity.view.V_Candidates;

/**
 * 候補者情報ビューのリポジトリ
 *
 * @author TC-0115
 *
 */
@Repository
public interface CandidatesViewRepository extends JpaRepository<V_Candidates, Integer>, CandidatesViewRepositoryCustom{//
	public V_Candidates findByCandidateId(Integer cId);

	public List<V_Candidates> findBySlcStatusDtlIdOrSlcStatusDtlId(Integer ssdId1, Integer ssdId2);

	public List<V_Candidates> findAllBySlcDateLessThan(Date date);

	public List<V_Candidates> findByDeleteFlagOrderByCandidateId(Integer DeleteFlagConstant);
}

