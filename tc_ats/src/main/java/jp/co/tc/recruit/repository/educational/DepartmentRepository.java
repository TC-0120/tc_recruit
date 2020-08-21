package jp.co.tc.recruit.repository.educational;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.tc.recruit.entity.educational.Department;

/**
 *
 * @author TC-0120
 *学科マスタ用リポジトリ
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	public List<Department> findByDeleteFlagOrderByDepartmentId(Integer DeleteFlagConstant);
}
