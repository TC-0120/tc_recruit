package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.tc.recruit.view.CandidatesView;

@Repository
public interface CandidatesViewRepository extends JpaRepository<CandidatesView, Integer>, CandidatesViewRepositoryCustom {
	public CandidatesView findByCandidateId(Integer cId);
}

