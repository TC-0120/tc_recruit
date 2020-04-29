package jp.co.tc.recruit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.MessageStatus;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Integer>{
	public MessageStatus findByStatusMessageId(Integer statusMessageId);
}
