package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.repository.TransitionPopulationViewRepository;

@Service
public class TransitionPopulationViewService {
	@Autowired
	TransitionPopulationViewRepository transitionPopulationViewRepository;

	public int CountBySlcIdAndSlcDtlId(Integer selectionStatusId, Integer selectionStatusDetailId) {
		return transitionPopulationViewRepository.findBySelectionStatusIdAndSelectionStatusDetailIdOrderByCandidateId(
				selectionStatusId, selectionStatusDetailId).size();
	}
}
