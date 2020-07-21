package jp.co.tc.recruit.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.entity.Selection.SelectionPK;

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
	@Query(value="DELETE FROM XXTC_SELECTION WHERE candidate_id = :id", nativeQuery=true)
	public Integer deleteByCandidateId(@Param("id") Integer id);

}
