package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.TotalSelectionView;

public interface TotalSelectionRepository extends JpaRepository<TotalSelectionView, Integer>{
	public TotalSelectionView findByStatusMessageId(Integer statusMessageId);
}
