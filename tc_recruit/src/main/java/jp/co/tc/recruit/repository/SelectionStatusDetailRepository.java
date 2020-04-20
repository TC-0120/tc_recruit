package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatusDetail;

public interface SelectionStatusDetailRepository extends JpaRepository<SelectionStatusDetail, Integer> {

	public List<SelectionStatusDetail> findByOrderBySlcStatusDtlId();

	public SelectionStatusDetail findBySlcStatusDtlId(Integer id);
}
