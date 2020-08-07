package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.Dashboard;

public interface MessageStatusRepository extends JpaRepository<Dashboard, Integer>, MessageStatusRepositoryCustom{
	public Dashboard findByStatusMessageId(Integer statusMessageId);
	public List<Dashboard> findAllByOrderBySort();
	public List<Dashboard> findByOrderByMessageStatusFlagAscSelectionStatusIdAscSelectionStatusDetailIdAsc();
}
