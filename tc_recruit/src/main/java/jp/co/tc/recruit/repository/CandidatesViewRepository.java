package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.tc.recruit.view.CandidatesView;

@Repository
public interface CandidatesViewRepository extends JpaRepository<CandidatesView, Integer> {

	public List<CandidatesView> findBySlcStatusIdAndSlcStatusDtlId(Integer ssId, Integer ssdId);

	public List<CandidatesView> findBySlcStatusId(Integer ssId);

	public List<CandidatesView> findBySlcStatusDtlId(Integer ssdId);

	public List<CandidatesView> findBySlcStatusDtlIdOrSlcStatusDtlId(Integer ssdId1, Integer ssdId2);

	public List<CandidatesView> findBySlcStatusIdAndSlcStatusDtlIdOrSlcStatusDtlId(Integer ssId, Integer ssdId1, Integer ssdId2);
}

