package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.SelectionStatusDetail;
import jp.co.tc.recruit.repository.SelectionStatusDetailRepository;

@Service
public class SelectionStatusDetailService {

	@Autowired
	SelectionStatusDetailRepository slcStatusDtlRepo;

	public List<SelectionStatusDetail> findAll() {
		return slcStatusDtlRepo.findByOrderBySlcStatusDtlId();
	}

}
