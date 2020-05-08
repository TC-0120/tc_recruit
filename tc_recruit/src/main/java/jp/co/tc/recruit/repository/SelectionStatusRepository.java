package jp.co.tc.recruit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatus;

/**
 * 選考ステータスのリポジトリ
 *
 * @author TC-0115
 *
 */
public interface SelectionStatusRepository extends JpaRepository<SelectionStatus, Integer> {

	public SelectionStatus findBySlcStatusId(Integer id);

	/*松原*/
	public List<SelectionStatus> findByOrderBySlcStatusId();
}
