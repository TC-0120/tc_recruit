package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.repository.CheckMessageRepository;
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


	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		/*全選考ステータス名称を取得　→　エントリーは不要
		model.addAttribute("selection", slcStatusRepo.findAll());
		*/

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
		Integer i = 1;
		String totalStatus[] = { "ttlDocument", "ttlAptitude", "ttlFirst", "ttlSecond", "ttlLast",
				"ttlOffer", "ttlBriefing" };

		for (String ttl : totalStatus) {
			model.addAttribute(ttl, ttlStatusRepo.findBySelectionStatusId(i));
			i++;
		}

		/*要対応事項のステータス名称をメッセージマスタから取得*/
		model.addAttribute("chkMsg", chkMsgRepo.findAll());
		
		/*要対応ステータスごとの人数を集計*/
		model.addAttribute("ttlChk", ttlChkRepo.findAll());
		/*Integer n = 1;
		String totalCheckStatus [] = {"chkBriefingAdjust", "chkBriefingAssesment", "checkDocument",
				"chkFirstAdjust", "chkFirstAssesment", "chkSecondAdjust", "chkSecondAssesment", "chkLastAdjust", "chkLastAssesment"};
		*/

		/*メッセージマスタで管理予定*/
		model.addAttribute("underSelection", "選考中");
		model.addAttribute("adjustment", "要対応");

		model.addAttribute("document", "書類選考");
		model.addAttribute("aptitude", "適性検査");
		model.addAttribute("first", "1次面接");
		model.addAttribute("second", "2次面接");
		model.addAttribute("last", "最終面接");
		model.addAttribute("offer", "内定");
		model.addAttribute("briefing", "説明会");


		return "/dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/

}
