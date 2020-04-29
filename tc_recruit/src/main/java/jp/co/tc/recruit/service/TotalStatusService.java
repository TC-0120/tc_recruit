package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.TotalStatusView;
import jp.co.tc.recruit.repository.TotalStatusRepository;

@Service
public class TotalStatusService {

	@Autowired
	TotalStatusRepository ttlSttRepo;

	public List<TotalStatusView> findBySelectionStatusId(Integer selectionStatusId) {
		return ttlSttRepo.findBySelectionStatusId(selectionStatusId);
	}

}
