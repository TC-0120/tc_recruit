package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	public Candidate findByCandidateId(Integer id);

}
