package jp.co.tc.recruit.repository.educational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.educational.Faculty;
/**
 *
 * @author TC-0120
 *学部マスタ用リポジトリ
 */
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
	public List<Faculty> findByDeleteFlagOrderByFacultyId(Integer DeleteFlagConstant);
}
