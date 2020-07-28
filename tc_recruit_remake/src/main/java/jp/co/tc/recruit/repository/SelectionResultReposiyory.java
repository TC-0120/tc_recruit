package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionResult;

public interface SelectionResultReposiyory extends JpaRepository<SelectionResult, Integer>{

	public List<SelectionResult> findByOrderBySelectionResultId();
}
