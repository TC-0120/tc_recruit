package jp.co.tc.recruit.repository.educational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.educational.EducationalBackground;
/**
 *
 * @author TC-0120
 *学歴情報テーブル用リポジトリ
 */
public interface EducationalBackgroundRepository extends JpaRepository<EducationalBackground, Integer>{
	public List<EducationalBackground>  findByDeleteFlagOrderByEducationalBackgroundId(Integer DeleteFlagConstant);
}
