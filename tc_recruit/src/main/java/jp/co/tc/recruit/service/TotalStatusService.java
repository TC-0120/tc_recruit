package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.repository.TotalStatusRepository;
import jp.co.tc.recruit.view.TotalStatusView;

public class TotalStatusService {
	@Autowired
	TotalStatusRepository ttlStatusRepo;

	public TotalStatusView findBySelectionStatusId(Integer selectionStatusId) {
		return findBySelectionStatusId(selectionStatusId);
	}

}
