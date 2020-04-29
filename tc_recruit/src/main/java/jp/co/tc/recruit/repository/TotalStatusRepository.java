package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.TotalStatusView;

public interface TotalStatusRepository extends JpaRepository<TotalStatusView, Integer>{
	public List<TotalStatusView> findBySelectionStatusId(Integer selctionStatusId);
}
