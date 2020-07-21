package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.LatestPlanView;

public interface LatestPlanRepository extends JpaRepository<LatestPlanView, Integer>{
	public List<LatestPlanView> findAll();

}
