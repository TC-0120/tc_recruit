package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.statistics.Statistics;
import jp.co.tc.recruit.entity.statistics.StatisticsConfig;

/**
 * 統計設定テーブル用リポジトリ
 * @author TC-0120
 *
 */
public interface StatisticsRepository extends JpaRepository<Statistics, Integer>{

	public Statistics findByStatisticsIdAndDeleteFlag(Integer statisticsId, Integer deleteFlagConstant);

	public List<Statistics> findByDeleteFlagOrderByStatisticsId(Integer deleteFlagConstant);

	public List<Statistics> findByStatisticsConfigAndDeleteFlagOrderByStatisticsId(StatisticsConfig statisticsConfig,Integer deleteFlagConstant);
}
