package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.repository.SelectionStatusRepository;

@Service
public class SelectionStatusService {

	@Autowired
	SelectionStatusRepository slcStatusRepo;

	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findByOrderBySlcStatusId();
	}

}
