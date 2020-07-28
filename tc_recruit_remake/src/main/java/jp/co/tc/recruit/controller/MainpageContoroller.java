package jp.co.tc.recruit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.form.CandidateForm;
import jp.co.tc.recruit.service.AgentService;
import jp.co.tc.recruit.service.CandidateService;
import jp.co.tc.recruit.service.SelectionResultService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.util.BeanCopy;

@Controller
@RequestMapping(path = "/mainpage")
public class MainpageContoroller {

	@Autowired
	CandidateService cddSvc;
	@Autowired
	AgentService agentSvc;
	@Autowired
	SelectionStatusService selectionStatusSvc;
	@Autowired
	SelectionResultService selectionResultSvc;

	/**
	 * メインページを表示するためのコントローラー
	 * 候補者情報と画面表示のための情報をスコープに格納
	 * @param model
	 * @return
	 */
	@GetMapping
	public String mainPageSet(Model model) {
		List<Candidate> candidates = cddSvc.findAll();
		//		for(Agent v:agents) {
		//			System.out.println(v.getAgentId());
		//			System.out.println(v.getAgentName());
		//			System.out.println(v.getReferrerFee());
		//		}
		model.addAttribute("candidates", candidates);

		//画面表示用のデータをスコープに格納
		model.addAttribute("agents", agentSvc.findAll());
		model.addAttribute("slcStatusList", selectionStatusSvc.findAll());
		model.addAttribute("slcResults", selectionResultSvc.findAll());
		return "mainpage";
	}

	/**
	 * 候補者情報を更新するためのコントローラー
	 * フォームクラスからエンティティに値を受け渡して更新を行う
	 * @param model
	 * @param candidateForm
	 * @return
	 */
	@PostMapping
	public String mainPageUpdate(Model model, CandidateForm candidateForm) {
		//入力情報とステータス情報をもとにエンティティに値を格納
		List<Candidate> candidates = BeanCopy.copyFormToEntity(candidateForm, selectionStatusSvc.findAll());
		cddSvc.update(candidates);
		return "redirect:/mainpage";
	}

	@GetMapping(path = "/register")
	public String register(Model model) {
		cddSvc.register();
		return "redirect:/mainpage";
	}
}
