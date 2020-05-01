package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Referrer;
import jp.co.tc.recruit.repository.ReferrerRepository;

/**
 * 紹介元のサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class ReferrerService {

	@Autowired
	ReferrerRepository referrerRepo;

	public List<Referrer> findAll() {
		return referrerRepo.findByOrderByReferrerId();
	}

}
