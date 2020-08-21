package jp.co.tc.recruit.service.educational;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.educational.Department;
import jp.co.tc.recruit.repository.educational.DepartmentRepository;

/**
*
* @author TC-0120
*学科マスタ用サービスクラス
*/
@Service
public class DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	public List<Department> findAll(){
		return departmentRepository.findByDeleteFlagOrderByDepartmentId(DeleteFlagConstant.NOT_DELETED);
	}
}
