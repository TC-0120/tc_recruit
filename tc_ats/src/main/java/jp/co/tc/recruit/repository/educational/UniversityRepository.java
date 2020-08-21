package jp.co.tc.recruit.repository.educational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.educational.University;
/**
 *
 * @author TC-0120
 *大学マスタ用リポジトリリポジトリ
 */
public interface UniversityRepository extends JpaRepository<University, Integer>{
	public List<University> findByDeleteFlagOrderByUniversityId(Integer DeleteFlagConstant);
}
