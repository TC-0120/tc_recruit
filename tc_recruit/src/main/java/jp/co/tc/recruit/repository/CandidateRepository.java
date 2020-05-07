package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Candidate;

/**
 * 候補者情報のリポジトリ
 *
 * @author TC-0115
 *
 */
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	public Candidate findByCandidateId(Integer id);

}
