package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.entity.MessageStatus;
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
	TotalStatusRepository ttlSttRepo;
	@Autowired
	TotalCheckRepository ttlChkRepo;
	@Autowired
	CheckMessageRepository chkMsgRepo;
	@Autowired
	MessageStatusRepository msgSttRepo;
	@Autowired
	MessageStatus slcStt;
	@Autowired
	MessageStatus chkStt;
	@Autowired
	TotalStatusView ttlSttViw;

	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		/*全ステータス名称を取得*/
		Integer msgId;
		for (msgId = 1; msgId < 9; msgId++) {
			slcStt = msgSttRepo.findByStatusMessageId(msgId);
			model.addAttribute("slcStt", slcStt);
		}
		for (msgId = 9; msgId < 19; msgId++) {
			chkStt = msgSttRepo.findByStatusMessageId(msgId);
			model.addAttribute("chkStt", chkStt);
		}

		/*選考ステータスごとの人数を集計*/
		Integer slcSttId;
		for (slcSttId = 1; slcSttId < 9; slcSttId++) {
			ttlSttViw = ttlSttRepo.findBySelectionStatusId(slcSttId);
			model.addAttribute("ttlSttViw", ttlSttViw);
		}

		/*選考中の候補者全数*/
		Integer allTtlSttViw = 0;
		Integer ttlSttViwCnt = 0;
		for (slcSttId = 1; slcSttId < 9; slcSttId++) {
			ttlSttViwCnt = ttlSttRepo.findBySelectionStatusId(slcSttId).getCount();
			allTtlSttViw += ttlSttViwCnt;
		}
		model.addAttribute("allTtlSttViw", allTtlSttViw);

		/*要対応ステータスごとの人数を集計*/
		Integer ttlChkViw1 = ttlChkRepo.findByMessageId(1).getTtlExcAsm();
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
		model.addAttribute("ttlChkViw", ttlChkViw9);

		/*Integer chkMsgId;
		   Integer ttlChkViw;
		 for (chkMsgId = 1; chkMsgId < 10; chkMsgId++) {
			ttlChkViw = ttlChkRepo.findByMessageId(chkMsgId);
		model.addAttribute("ttlChkViw", ttlChkViw);
		}
		*/

		/*要対応事項の全数*/
		Integer ttlChkViwCnt = ttlChkViw1 + ttlChkViw2 + ttlChkViw3 + ttlChkViw4
				+ ttlChkViw5 + ttlChkViw6 + ttlChkViw7 + ttlChkViw8 + ttlChkViw9;
		model.addAttribute("ttlChkViwCnt", ttlChkViwCnt);

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
