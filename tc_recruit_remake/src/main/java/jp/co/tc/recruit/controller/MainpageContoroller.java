package jp.co.tc.recruit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	HttpSession httpSession;

	/**
	 * メインページを表示するためのコントローラー
	 * 候補者情報と画面表示のための情報をスコープに格納
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping
	public String getMainPage(Model model, RedirectAttributes redirectAttributes) {
		List<Candidate> candidates = cddSvc.findAll();
		if (model.containsAttribute("candidateForm")) {
			CandidateForm candidateForm = (CandidateForm) model.getAttribute("candidateForm");
			//ソート条件がある場合はその値を各ソートナンバーのvalue値に設定
			model.addAttribute("sortNumber" + candidateForm.getSortType(),
					candidateForm.getSortNumber().get(candidateForm.getSortType()));
			//更新後もソート条件を維持するためにスコープに格納
			model.addAttribute("sortType", candidateForm.getSortType());
			candidates = cddSvc.sortCandidate(candidateForm.getSortNumber(), candidateForm.getSortType());
		}
		if (model.containsAttribute("candidateForm")) {
			System.out.println("リダイレクト確認：" + ((CandidateForm) model.getAttribute("candidateForm")).getSortType());
		}

		//候補者情報をスコープに格納
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
	public String mainPageUpdate(Model model, CandidateForm candidateForm, RedirectAttributes redirectAttributes) {
		//ソート番号をフラッシュスコープに格納
		redirectAttributes.addFlashAttribute("candidateForm", candidateForm);
		//System.out.println(candidateForm.getCandidateId());
		//入力情報とステータス情報をもとにエンティティに値を格納
		if (candidateForm.getCandidateId() != null && candidateForm.getSortCheck() == 0) {
			//System.out.println(candidateForm.getEduBack().get(0));
			cddSvc.update(BeanCopy.copyFormToEntity(candidateForm, selectionStatusSvc.findAll()));
		}
		return "redirect:/mainpage";
	}

	@GetMapping(path = "/register")
	public String register(Model model) {
		cddSvc.register();
		return "redirect:/mainpage";
	}
}
