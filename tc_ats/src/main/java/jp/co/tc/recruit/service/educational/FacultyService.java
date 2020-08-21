package jp.co.tc.recruit.service.educational;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.educational.Faculty;
import jp.co.tc.recruit.repository.educational.FacultyRepository;
/**
 *
 * @author TC-0120
 *学部マスタ用サービスクラス
 */
@Service
public class FacultyService {

	@Autowired
	FacultyRepository facultyRepository;

	public List<Faculty> findAll() {
		return facultyRepository.findByDeleteFlagOrderByFacultyId(DeleteFlagConstant.NOT_DELETED);
	}
}
