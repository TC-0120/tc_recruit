package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.view.V_TransitionPopulation;

public interface TransitionPopulationViewRepository extends JpaRepository<V_TransitionPopulation, Integer> {

	public List<V_TransitionPopulation> findBySelectionStatusIdAndSelectionStatusDetailIdOrderByCandidateId(
			Integer selectionStatusId, Integer selectionStatusDetailId);
}
