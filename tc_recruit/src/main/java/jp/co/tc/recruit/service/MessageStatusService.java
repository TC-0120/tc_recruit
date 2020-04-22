package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.repository.MessageStatusRepository;

@Service
public class MessageStatusService {

	@Autowired
	MessageStatusRepository MsgSttRepo;

	public MessageStatus findByStatusMessageId(Integer sttMsgId) {
		return findByStatusMessageId(sttMsgId);
	}

}
