package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.CheckMessage;
import jp.co.tc.recruit.repository.CheckMessageRepository;

@Service
public class CheckMessageService {

	@Autowired
	CheckMessageRepository chkMsgRepo;

	public List<CheckMessage> findAll() {
		return chkMsgRepo.findAll();
	}

}
