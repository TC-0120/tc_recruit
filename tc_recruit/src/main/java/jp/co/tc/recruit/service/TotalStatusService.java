package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.repository.TotalStatusRepository;

public class TotalStatusService {
	@Autowired
	TotalStatusRepository ttlStatusRepo;

	public Integer findBySelectionStatusId(Integer selectionStatusId) {
		return findBySelectionStatusId(selectionStatusId);
	}

}
