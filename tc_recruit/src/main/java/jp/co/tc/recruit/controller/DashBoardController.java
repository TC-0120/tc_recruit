package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.entity.TotalCheckView;
import jp.co.tc.recruit.entity.TotalStatusView;
import jp.co.tc.recruit.repository.CheckMessageRepository;
import jp.co.tc.recruit.repository.MessageStatusRepository;
import jp.co.tc.recruit.repository.TotalCheckRepository;
import jp.co.tc.recruit.repository.TotalStatusRepository;

@Controller
public class DashBoardController {

	/*	@Autowired
		SelectionStatusRepository slcStatusRepo;*/

	@Autowired
	TotalStatusRepository ttlStatusRepo;

	@Autowired
	TotalCheckRepository ttlChkRepo;

	@Autowired
	CheckMessageRepository chkMsgRepo;

	@Autowired
	MessageStatusRepository msgSttRepo;



	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		/*全ステータス名称を取得*/
		Integer i;
		MessageStatus status;
		for(i = 1; i < 19; i++) {
		status = msgSttRepo.findByStatusMessageId(i);
		model.addAttribute("status", status);
		}

		/*選考中の候補者全数*/
		Integer k;
		Integer allStatus = 0;
		Integer all;
		for (k = 1; k < 9; k++) {
			all = ttlStatusRepo.findBySelectionStatusId(k).getCount();
			allStatus += all;
		}
		model.addAttribute("allStatus", allStatus);

		/*選考ステータスごとの人数を集計*/
		Integer slcSttId;
		TotalStatusView ttlSttViw;
		for(slcSttId = 1; slcSttId < 9; slcSttId++) {
			ttlSttViw = ttlStatusRepo.findBySelectionStatusId(slcSttId);
			model.addAttribute("ttlSttViw", ttlSttViw);
		}

		/*要対応ステータスごとの人数を集計*/
		Integer msgId;
		TotalCheckView ttlCheck;
		for(msgId = 1; msgId < 10; msgId++) {
			ttlCheck = ttlChkRepo.findByMessageId(msgId);
		model.addAttribute("ttlChk", ttlCheck);
		}

		/*Integer n = 1;
		String totalCheckStatus [] = {"chkBriefingAdjust", "chkBriefingAssesment", "checkDocument",
				"chkFirstAdjust", "chkFirstAssesment", "chkSecondAdjust", "chkSecondAssesment", "chkLastAdjust", "chkLastAssesment"};
		*/


		return "/dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/

}
