package jp.co.tc.recruit.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.tc.recruit.entity.selection.Selection;
import jp.co.tc.recruit.entity.selection.Selection.SelectionPK;

/**
 * 選考情報のリポジトリ
 *
 * @author TC-0115
 *
 */
@Transactional
public interface SelectionRepository extends JpaRepository<Selection, SelectionPK> {
	public Selection findBySlcPK(SelectionPK slcPK);

	@Modifying
	@Query(value="DELETE FROM XXTC_SELECTION_DATE WHERE candidate_id = :id", nativeQuery=true)
	public Integer deleteByCandidateId(@Param("id") Integer id);

	@Modifying
	@Query(value="SELECT * FROM XXTC_SELECTION_DATE WHERE candidate_id = :id", nativeQuery=true)
	public List<Selection> findByCandidateId(@Param("id") Integer id);


}
