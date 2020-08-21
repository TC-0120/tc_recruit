package jp.co.tc.recruit.repository.view;

import java.util.List;

import jp.co.tc.recruit.entity.view.V_TransitionPopulation;

public interface V_TransitionPopulationRepositoryCustom {
	public List<V_TransitionPopulation> findByInsertYear(Integer year);
}
