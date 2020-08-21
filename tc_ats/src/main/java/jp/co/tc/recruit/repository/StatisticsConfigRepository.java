package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.statistics.StatisticsConfig;

public interface StatisticsConfigRepository extends JpaRepository<StatisticsConfig, Integer>{
	public StatisticsConfig findByStatisticsConfigIdAndDeleteFlag(Integer statisticsConfigId,Integer DeleteFlagConstant);
}
