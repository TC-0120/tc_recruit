package jp.co.tc.recruit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.SelectionStatusDetail;

/**
 * 選考ステータス詳細のリポジトリ
 *
 * @author TC-0115
 *
 */
public interface SelectionStatusDetailRepository extends JpaRepository<SelectionStatusDetail, Integer> {

	public List<SelectionStatusDetail> findAllByOrderBySlcStatusDtlId();

	public SelectionStatusDetail findBySlcStatusDtlId(Integer id);
}
