package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.repository.TotalStatusRepository;

@Controller
public class DashBoardController {

	/*	@Autowired
		SelectionStatusRepository slcStatusRepo;*/

	@Autowired
	TotalStatusRepository ttlStatusRepo;

	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		/*全選考ステータス名称を取得　→　エントリーは不要
		model.addAttribute("selection", slcStatusRepo.findAll());
		*/

		/*選考ステータスごとの人数を集計*/
		Integer i = 1;
		String totalStatus[] = { "ttlDocument", "ttlAptitude", "ttlFirst", "ttlSecond", "ttlFinal",
				"ttlOffer", "ttlBriefing" };

		for (String ttl : totalStatus) {
			model.addAttribute(ttl, ttlStatusRepo.findBySelectionStatusId(i));
			i++;
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

		/*メッセージマスタで管理予定*/
		model.addAttribute("underSelection", "選考中");
		model.addAttribute("adjustment", "要対応");

		model.addAttribute("document", "書類選考");
		model.addAttribute("aptitude", "適性検査");
		model.addAttribute("first", "1次面接");
		model.addAttribute("second", "2次面接");
		model.addAttribute("final", "最終面接");
		model.addAttribute("offer", "内定");
		model.addAttribute("briefing", "説明会");

		model.addAttribute("checkDocument", "書類選考");
		model.addAttribute("checkAptitude", "適性検査");
		model.addAttribute("checkFirst", "1次面接");
		model.addAttribute("checkSecond", "2次面接");
		model.addAttribute("checkFinal", "最終面接");
		model.addAttribute("checkOffer", "内定");
		model.addAttribute("checkBriefing", "説明会");

		return "/dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/

}
