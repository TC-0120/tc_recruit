package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Referrer;
import jp.co.tc.recruit.repository.ReferrerRepository;

@Service
public class ReferrerService {

	@Autowired
	ReferrerRepository referrerRepo;

	public List<Referrer> findAll() {
		return referrerRepo.findAll(Sort.by("referrerId"));
	}

}
