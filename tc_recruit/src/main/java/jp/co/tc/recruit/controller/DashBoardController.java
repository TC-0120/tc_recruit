package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.repository.SelectionStatusRepository;
import jp.co.tc.recruit.repository.TotalStatusRepository;

@Controller
public class DashBoardController {

	@Autowired
	SelectionStatusRepository slcStatusRepo;
	@Autowired
	TotalStatusRepository ttlStatusRepo;

	@GetMapping("/dashboard")
	public String getLogin(Model model) {

		/*選考ステータスごとの人数を集計*/
		Integer i = 1;
		String totalStatus[] = { "ttlEntry", "ttlDocument", "ttlAptitude", "ttlFirst", "ttlSecond", "ttlFinal",
				"ttlSecretlyDetermin", "ttlBriefing" };

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
		model.addAttribute("Adjustment", "要対応");

		/*全ステータス名称を取得*/
		model.addAttribute("selection", slcStatusRepo.findAll());

		return "/dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/
	

}
