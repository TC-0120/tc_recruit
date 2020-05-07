package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.tc.recruit.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	public Candidate findByCandidateId(Integer id);

	@Query(value="DELETE FROM XXTC_CANDIDATE WHERE candidate_id = :id", nativeQuery=true)
	public void deleteByCandidtaeId(@Param("id") Integer id);
}
