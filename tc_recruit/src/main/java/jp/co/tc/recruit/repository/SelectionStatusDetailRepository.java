package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatusDetail;

public interface SelectionStatusDetailRepository extends JpaRepository<SelectionStatusDetail, Integer> {
	public SelectionStatusDetail findBySlcStatusDtlId(Integer id);
}
