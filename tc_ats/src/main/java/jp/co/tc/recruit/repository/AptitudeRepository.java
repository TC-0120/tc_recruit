package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Aptitude;

/**
 *
 * @author TC-0120
 *適性検査マスタのリポジトリ
 */
public interface AptitudeRepository extends JpaRepository<Aptitude, Integer>{
	public List<Aptitude> findByDeleteFlagOrderByAptitudeId(Integer DeleteFlagConstant);
}
