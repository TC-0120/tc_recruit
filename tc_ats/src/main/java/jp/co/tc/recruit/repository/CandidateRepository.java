package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Candidate;

/**
 * 候補者情報のリポジトリ
 *
 * @author TC-0115
 *
 */
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	public Candidate findByCandidateIdAndDeleteFlag(Integer id,Integer deleteFlag);
	public List<Candidate> findAll();

}
