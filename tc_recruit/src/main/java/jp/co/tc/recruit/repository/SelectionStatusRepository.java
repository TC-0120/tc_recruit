package jp.co.tc.recruit.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatus;

public interface SelectionStatusRepository extends JpaRepository<SelectionStatus, Integer> {
	/*public SelectionRepository findBySlcPK(Integer slcPK);*/

}
