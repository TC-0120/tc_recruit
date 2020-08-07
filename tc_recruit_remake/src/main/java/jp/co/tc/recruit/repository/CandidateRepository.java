package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.tc.recruit.entity.Candidate;

/**
 * 候補者情報のリポジトリ
 *
 * @author TC-0115
 *
 */
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	public Candidate findByCandidateId(Integer id);
	public List<Candidate> findByOrderByCandidateId();

	@Query("SELECT c FROM Candidate c WHERE c.slcStatus.slcStatusId = :slcStatusId ORDER BY c.candidateId ASC")
	public List<Candidate> findBySelectionStatusOrderByCandidateId(@Param("slcStatusId")Integer slcStatusId);

}
