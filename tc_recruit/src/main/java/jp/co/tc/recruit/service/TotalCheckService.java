package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.TotalCheckView;
import jp.co.tc.recruit.repository.TotalCheckRepository;

@Service
public class TotalCheckService {

	@Autowired
	TotalCheckRepository ttlChkRepo;

	public TotalCheckView findByStatusMessageId(Integer StatusMessageId) {
		return ttlChkRepo.findByStatusMessageId(StatusMessageId);
	}

	public List<TotalCheckView> findAll() {
		return ttlChkRepo.findAll();
	}

	public List<TotalCheckView> findAllByOrderByStatusMessageId(){
		return ttlChkRepo.findAllByOrderByStatusMessageId();
	}

	public List<TotalCheckView> findAllByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc(){
		return ttlChkRepo.findAllByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc();
	}

}
