package jp.co.tc.recruit.service.educational;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.educational.University;
import jp.co.tc.recruit.repository.educational.UniversityRepository;
/**
 *
 * @author TC-0120
 *大学マスタ用サービスクラス
 */
@Service
public class UniversityService {

	@Autowired
	UniversityRepository universityRepository;

	public List<University> findAll(){
		return universityRepository.findByDeleteFlagOrderByUniversityId(DeleteFlagConstant.NOT_DELETED);
	}
}
