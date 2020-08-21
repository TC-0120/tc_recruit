package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.Dashboard;
import jp.co.tc.recruit.repository.DashboardRepository;

@Service
public class DashboardService {

	@Autowired
	DashboardRepository dashboardRepository;

	public List<Dashboard> findAll(){
		return dashboardRepository.findByDeleteFlagOrderByStatusMessageProcedure(DeleteFlagConstant.NOT_DELETED);
	}
}
