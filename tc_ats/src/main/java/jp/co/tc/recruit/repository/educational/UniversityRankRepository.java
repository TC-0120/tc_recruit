package jp.co.tc.recruit.repository.educational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.educational.UniversityRank;

/**
 *
 * @author TC-0120
 *大学ランクマスタ用リポジトリ
 */
public interface UniversityRankRepository extends JpaRepository<UniversityRank, Integer>{
	public List<UniversityRank> findByDeleteFlagOrderByUniversityRankId(Integer DeleteFlagConstant);

	public UniversityRank findFirstByDeleteFlagOrderByUniversityRankId(Integer DeleteFlagConstant);
}
