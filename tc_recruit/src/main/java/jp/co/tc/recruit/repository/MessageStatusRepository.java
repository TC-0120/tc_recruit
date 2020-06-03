package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.MessageStatus;

public interface MessageStatusRepository extends JpaRepository<MessageStatus, Integer>, MessageStatusRepositoryCustom{
	public MessageStatus findByStatusMessageId(Integer statusMessageId);
	public List<MessageStatus> findAllByOrderBySort();
	public List<MessageStatus> findByOrderByMessageStatusFlagAscSelectionStatusIdAscSelectionStatusDetailIdAsc();
}
