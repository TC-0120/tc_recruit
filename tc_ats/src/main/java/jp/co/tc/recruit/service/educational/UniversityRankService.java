package jp.co.tc.recruit.service.educational;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.educational.UniversityRank;
import jp.co.tc.recruit.repository.educational.UniversityRankRepository;
/**
 *
 * @author TC-0120
 *大学ランク用サービスクラス
 */
@Service
public class UniversityRankService {

	@Autowired
	UniversityRankRepository universityRankRepository;

	public List<UniversityRank> findAll() {
		return universityRankRepository.findByDeleteFlagOrderByUniversityRankId(DeleteFlagConstant.NOT_DELETED);
	}
}
