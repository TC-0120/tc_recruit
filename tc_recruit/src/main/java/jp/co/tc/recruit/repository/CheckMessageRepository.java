package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.CheckMessage;

public interface CheckMessageRepository extends JpaRepository<CheckMessage, Integer>{
	public List<CheckMessage> findAll();

}
