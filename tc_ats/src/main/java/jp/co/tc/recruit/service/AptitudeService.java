package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.Aptitude;
import jp.co.tc.recruit.repository.AptitudeRepository;

/**
 *
 * @author TC-0120
 *適性検査マスタ用のサービスクラス
 */
@Service
public class AptitudeService {

	@Autowired
	AptitudeRepository aptitudeRepository;

	public List<Aptitude> findAll() {
		return aptitudeRepository.findByDeleteFlagOrderByAptitudeId(DeleteFlagConstant.NOT_DELETED);
	}
}
