package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.TotalCheckView;
import jp.co.tc.recruit.repository.TotalCheckRepository;

@Service
public class TotalCheckService {

	@Autowired
	TotalCheckRepository ttlChkRepo;

	public TotalCheckView findByMessageId(Integer messageId) {
		return ttlChkRepo.findByMessageId(messageId);
	}

}
