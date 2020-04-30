package jp.co.tc.recruit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.service.CheckMessageService;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.TotalCheckService;
import jp.co.tc.recruit.service.TotalStatusService;

@Controller
public class DashBoardController {

	@Autowired
	TotalStatusService ttlSttSvc;
	@Autowired
	TotalCheckService ttlChkSvc;
	@Autowired
	CheckMessageService chkMsgSvc;
	@Autowired
	MessageStatusService msgSttSvc;

	@GetMapping
	public String getDashBoard(Model model) {

		/*全ステータス名称を取得*/
		Integer msgId;
		MessageStatus msgStt;
		MessageStatus chkStt;
		List<MessageStatus> msgSttList = new ArrayList<MessageStatus>();
		List<MessageStatus> chkSttList = new ArrayList<MessageStatus>();
		for (msgId = 1; msgId < 9; msgId++) {
			msgStt = msgSttSvc.findByStatusMessageId(msgId);
			msgSttList.add(msgStt);
		}
		model.addAttribute("msgSttList", msgSttList);

		for (msgId = 9; msgId < 19; msgId++) {
			chkStt = msgSttSvc.findByStatusMessageId(msgId);
			chkSttList.add(chkStt);
		}
		model.addAttribute("chkSttList", chkSttList);

		/*選考ステータスごとの人数を集計*/
		/*Integer slcSttId;
		List<TotalStatusView> ttlStt = new ArrayList<TotalStatusView>();
		List<TotalStatusView> ttlSttList = new ArrayList<TotalStatusView>();
		for (slcSttId = 1; slcSttId < 9; slcSttId++) {
			ttlStt = ttlSttSvc.findBySelectionStatusId(slcSttId);
			ttlSttList.addAll(ttlStt);
		}
		model.addAttribute("ttlStt", ttlSttList);*/

		/*選考中の候補者全数*/
		/*s*/

		/*要対応ステータスごとの人数を集計*/
		/*Integer ttlChkViw1 = ttlChkRepo.findByMessageId(1).getTtlExcAsm();
		Integer ttlChkViw2 = ttlChkRepo.findByMessageId(2).getTtlExcAsm();
		Integer ttlChkViw3 = ttlChkRepo.findByMessageId(3).getTtlExcAsm();
		Integer ttlChkViw4 = ttlChkRepo.findByMessageId(4).getTtlExcAsm();
		Integer ttlChkViw5 = ttlChkRepo.findByMessageId(5).getTtlAsm();
		Integer ttlChkViw6 = ttlChkRepo.findByMessageId(6).getTtlExcAsm();
		Integer ttlChkViw7 = ttlChkRepo.findByMessageId(7).getTtlAsm();
		Integer ttlChkViw8 = ttlChkRepo.findByMessageId(8).getTtlExcAsm();
		Integer ttlChkViw9 = ttlChkRepo.findByMessageId(9).getTtlAsm();

		model.addAttribute("ttlChkViw", ttlChkViw1);
		model.addAttribute("ttlChkViw", ttlChkViw2);
		model.addAttribute("ttlChkViw", ttlChkViw3);
		model.addAttribute("ttlChkViw", ttlChkViw4);
		model.addAttribute("ttlChkViw", ttlChkViw5);
		model.addAttribute("ttlChkViw", ttlChkViw6);
		model.addAttribute("ttlChkViw", ttlChkViw7);
		model.addAttribute("ttlChkViw", ttlChkViw8);
		model.addAttribute("ttlChkViw", ttlChkViw9);*/

		/*Integer chkMsgId;
		   Integer ttlChkViw;
		 for (chkMsgId = 1; chkMsgId < 10; chkMsgId++) {
			ttlChkViw = ttlChkRepo.findByMessageId(chkMsgId);
		model.addAttribute("ttlChkViw", ttlChkViw);
		}
		*/

		/*要対応事項の全数
		Integer ttlChkViwCnt = ttlChkViw1 + ttlChkViw2 + ttlChkViw3 + ttlChkViw4
				+ ttlChkViw5 + ttlChkViw6 + ttlChkViw7 + ttlChkViw8 + ttlChkViw9;
		model.addAttribute("ttlChkViwCnt", ttlChkViwCnt);*/

		/*Integer allTtlChkViw = 0;
		Integer ttlChkViwCnt = 0;
		for (chkMsgId = 1; chkMsgId < 10; chkMsgId++) {
			ttlChkViwCnt = ttlChkRepo.findByMessageId(chkMsgId).getTtlExcAsm();
			allTtlChkViw += ttlChkViwCnt;
		}
		model.addAttribute("allTtlChkViw", allTtlChkViw);*/

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
