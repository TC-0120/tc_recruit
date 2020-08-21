package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Dashboard;

public interface DashboardRepository extends JpaRepository<Dashboard, Integer>{
	public List<Dashboard> findByDeleteFlagOrderByStatusMessageProcedure(Integer DeleteFlagConstant);
}
