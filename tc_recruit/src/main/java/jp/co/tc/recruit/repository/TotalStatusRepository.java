package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.TotalStatusView;

public interface TotalStatusRepository extends JpaRepository<TotalStatusView, Integer>{
	public TotalStatusView findBySelectionStatusId(Integer selctionStatusId);
}
