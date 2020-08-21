package jp.co.tc.recruit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.selection.SelectionStatus;

/**
 * 選考ステータスのリポジトリ
 *
 * @author TC-0115
 *
 */
public interface SelectionStatusRepository extends JpaRepository<SelectionStatus, Integer> {

	public SelectionStatus findBySlcStatusIdAndDeleteFlag(Integer id, Integer DeleteFlagConstant);
	public List<SelectionStatus> findAllByOrderBySlcStatusId();
	public List<SelectionStatus> findByDeleteFlagOrderBySelectionProcedure(Integer DeleteFlagConstant);
}
