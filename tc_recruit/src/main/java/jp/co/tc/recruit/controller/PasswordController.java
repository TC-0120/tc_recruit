package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.service.CandidateService;
import jp.co.tc.recruit.service.LatestPlanService;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.TotalCheckService;
import jp.co.tc.recruit.service.TotalSelectionService;

@Controller
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	TotalSelectionService ttlSlcSvc;
	@Autowired
	TotalCheckService ttlChkSvc;
	@Autowired
	MessageStatusService msgSttSvc;
	@Autowired
	LatestPlanService ltsPlnSvc;
	@Autowired
	CandidateService cddSvc;

	@GetMapping()
	public String getPasswordSet(Model model) {
		return "/password_setting";
	}

}
