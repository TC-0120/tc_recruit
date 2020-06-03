package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.LatestPlanView;
import jp.co.tc.recruit.repository.LatestPlanRepository;

@Service
public class LatestPlanService {

	@Autowired
	LatestPlanRepository ltsPlnRepo;

	public List<LatestPlanView> findAll() {
		return ltsPlnRepo.findAll();
	}

}
