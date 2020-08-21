package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.selection.SelectionReferrer;

public interface SelectionReferrerRepository extends JpaRepository<SelectionReferrer, Integer>{
	public List<SelectionReferrer> findByOrderBySelectionReferrerId();
}
