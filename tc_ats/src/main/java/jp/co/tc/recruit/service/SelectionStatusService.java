package jp.co.tc.recruit.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.selection.SelectionStatus;
import jp.co.tc.recruit.repository.SelectionStatusRepository;

/**
 * 選考ステータスのサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class SelectionStatusService {

	@Autowired
	SelectionStatusRepository slcStatusRepo;


	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findAllByOrderBySlcStatusId();
	}


}