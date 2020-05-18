package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.repository.MessageStatusRepository;

@Service
public class MessageStatusService {

	@Autowired
	MessageStatusRepository MsgSttRepo;

	public MessageStatus findByStatusMessageId(Integer statusMessageId) {
		return MsgSttRepo.findByStatusMessageId(statusMessageId);
	}

	public List<MessageStatus> findAll(){
		return MsgSttRepo.findAll();
	}

}
