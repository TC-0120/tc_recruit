package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.view.TotalSelectionView;

public interface TotalSelectionRepository extends JpaRepository<TotalSelectionView, Integer>{
	public TotalSelectionView findByStatusMessageId(Integer statusMessageId);
	public List<TotalSelectionView> findAllByOrderByStatusMessageId();
	public List<TotalSelectionView> findAllByOrderBySelectionStatusId();
	public List<TotalSelectionView> findAllByOrderBySort();
	public TotalSelectionView findBySort(Integer sort);
	public List<TotalSelectionView> findAll();
}
