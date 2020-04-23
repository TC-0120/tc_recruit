package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.TotalStatusView;
import jp.co.tc.recruit.repository.TotalStatusRepository;

@Service
public class TotalStatusService {

	@Autowired
	TotalStatusRepository ttlSttRepo;

	public TotalStatusView findBySelectionStatusId(Integer slcSttId) {
		return ttlSttRepo.findBySelectionStatusId(slcSttId);
	}

}
