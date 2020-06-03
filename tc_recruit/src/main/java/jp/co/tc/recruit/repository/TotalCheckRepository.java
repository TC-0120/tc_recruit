package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.TotalCheckView;

public interface TotalCheckRepository extends JpaRepository<TotalCheckView, Integer>{
	public TotalCheckView findByStatusMessageId(Integer statusMessageId);
	public List<TotalCheckView> findAll();
	public List<TotalCheckView> findAllByOrderByStatusMessageId();
	public List<TotalCheckView>findAllByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc();
	public List<TotalCheckView> findAllByOrderBySort();
	public TotalCheckView findBySort(Integer sort);
}
