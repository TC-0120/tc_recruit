package jp.co.tc.recruit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatus;

public interface SelectionStatusRepository extends JpaRepository<SelectionStatus, Integer> {
	public SelectionRepository findBySlcPK(Integer slcPK);

	/*松原*/
	public List<SelectionStatus> findAll();
}
