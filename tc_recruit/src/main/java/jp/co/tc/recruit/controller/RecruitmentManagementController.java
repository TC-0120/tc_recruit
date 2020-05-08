package jp.co.tc.recruit.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.entity.Selection.SelectionPK;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.form.MultipleUpdateForm;
import jp.co.tc.recruit.service.AgentService;
import jp.co.tc.recruit.service.CandidateService;
import jp.co.tc.recruit.service.CandidatesViewService;
import jp.co.tc.recruit.service.ReferrerService;
import jp.co.tc.recruit.service.SelectionService;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;

/**
 * 採用情報管理機能のコントローラー
 *
 * @author TC-0115
 */
@Controller
@RequestMapping("/recruit/candidates")
public class RecruitmentManagementController {

	@Autowired
	CandidateService candidateService;
	@Autowired
	SelectionService selectionService;
	@Autowired
	SelectionStatusService slcStatusService;
	@Autowired
	SelectionStatusDetailService slcStatusDtlService;
	@Autowired
	AgentService agentService;
	@Autowired
	ReferrerService referrerService;
	@Autowired
	CandidatesViewService candidatesViewService;

	/**
	 * 候補者一覧画面での候補者情報の検索、表示
	 *
	 * @param conditionsForm 検索条件
	 * @param model
	 * @return 一覧画面
	 */
	@GetMapping
	public String index(@ModelAttribute("conditionsForm") ConditionsForm conditionsForm, Model model) {
		//入力された検索条件から候補者情報を取得、格納
		model.addAttribute("candidates", candidatesViewService.findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(conditionsForm));
		//検索ドロップダウン用のリスト（選考ステータス、詳細）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		return "recruitment_management";
	}


	/**
	 * 候補者情報登録入力画面への遷移
	 *
	 * @param model
	 * @return 候補者情報登録入力画面
	 */
	@GetMapping("regist")
	public String registInput(Model model) {
		//登録ドロップダウン用のリスト（選考ステータス、詳細、エージェント、紹介元）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "candidate/regist_input";
	}

	/**
	 * 候補者情報を登録
	 *
	 * @param candidate 候補者情報
	 * @param slcDate 選考日程
	 * @return 一覧画面
	 */
	@PostMapping
	public String registComplete(@ModelAttribute Candidate candidate, @RequestParam("slcDate") String slcDate) {
		//候補者情報を登録
		candidateService.regist(candidate, slcDate);
		//選考情報を登録
		selectionService.regist(candidate.getCandidateId(), candidate.getSlcStatus().getSlcStatusId(), slcDate);
		return "redirect:/recruit/candidates";
	}

	/**
	 * 候補者情報変更入力画面への遷移
	 *
	 * @param id 候補者ID
	 * @param model
	 * @return 候補者情報変更入力画面
	 */
	@GetMapping("update")
	public String updateInput(@RequestParam("candidateId") Integer id, Model model) {
		//候補者IDから候補者情報を取得、格納
		model.addAttribute("candidate", candidateService.findById(id));
		//変更ドロップダウン用のリスト（選考ステータス、詳細、エージェント、紹介元）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "candidate/update_input";
	}

	/**
	 * 候補者情報を変更
	 *
	 * @param id 候補者ID
	 * @param candidate 候補者情報
	 * @return 一覧画面
	 */
	@PostMapping("{id}")
	public String updateComplete(@PathVariable Integer id, @ModelAttribute Candidate candidate) {
		//候補者情報を変更
		candidate.setCandidateId(id);
		candidateService.update(candidate);
		return "redirect:/recruit/candidates";
	}

	/**
	 * 候補者情報を削除
	 *
	 * @param id 候補者ID
	 * @return 一覧画面
	 */
	@GetMapping("{id}/delete")
	public String delete(@PathVariable Integer id) {
		//候補者情報を削除
		candidateService.delete(id);
		//選考情報を削除
		selectionService.deleteByCandidateId(id);
		return "redirect:/recruit/candidates";
	}

	/**
	 * 選考情報変更画面への遷移
	 *
	 * @param cId 候補者ID
	 * @param model
	 * @return 選考情報変更画面
	 */
	@GetMapping("selection")
	public String slcUpdateInput(@RequestParam("candidateId") Integer cId, Model model) {
		//候補者IDから候補者情報を取得
		Candidate candidate = candidateService.findById(cId);
		//候補者IDと選考ステータスIDから選考情報を取得
		Integer sId = candidate.getSlcStatus().getSlcStatusId();
		SelectionPK slcPK = new SelectionPK(cId, sId);
		Selection slc = selectionService.findById(slcPK);

		//候補者情報、選考情報を格納
		model.addAttribute("candidate", candidate);
		model.addAttribute("selection", slc);
		//選考日程をDate型からString型に変換、格納
		model.addAttribute("slcDateString", selectionService.getStringDate(slc.getSlcDate()));
		//選考結果を候補者情報から取得、格納
		model.addAttribute("slcResult", candidate.getSlcStatusDtl().getSlcStatusDtlId());
		return "selection/update_input";
	}


	/**
	 * 選考情報を変更
	 *
	 * @param slcResult 選考結果
	 * @param slc 選考情報
	 * @param slcDate 選考日程
	 * @return 一覧画面
	 */
	@PostMapping("selection")
	public String slcUpdateComplete(@RequestParam("slcResult") Integer slcResult, @ModelAttribute Selection slc, @RequestParam("slcDateString") String slcDate) {
		//選考日程をString型からDate型に変換、保存
		slc.setSlcDate(selectionService.setDate(slcDate));
		//選考情報を変更
		selectionService.save(slc);
		//選考ステータスを変更
		candidateService.slcStatusManagement(slc.getSlcPK().getCandidateId(), slcResult, slcDate);
		return "redirect:/recruit/candidates";
	}

	/**
	 * 選考ステータスの繰り上げ
	 *
	 * @param cId 候補者ID
	 * @param redirectAttributes
	 * @return 選考情報変更画面or一覧画面
	 */
	@PostMapping("seleciton/nextStatus")
	public String nextStatus(@RequestParam("candidateId") Integer cId, RedirectAttributes redirectAttributes) {
		if (candidateService.slcStatusUp(cId)) {
			//選考ステータスが最後（入社手続き）でない場合、選考情報変更画面に遷移
			redirectAttributes.addAttribute("candidateId", cId);
			return "redirect:/recruit/candidates/selection";
		} else {
			//選考ステータスが最後の場合、一覧画面に遷移
			return "redirect:/recruit/candidates";
		}
	}

	/**
	 * 一括更新画面への遷移
	 *
	 * @param cId 候補者ID（配列）
	 * @param muForm 一括更新のフォーム
	 * @param model
	 * @return 一括更新画面
	 */
	@GetMapping("multiple")
	public String multipleUpdate(@RequestParam("candidateId") Integer[] cId, @ModelAttribute("multipleUpdateForm") MultipleUpdateForm muForm, Model model) {
		//複数の候補者IDから候補者情報を取得、格納
		model.addAttribute("candidates", candidatesViewService.getCandidatesByCandidateId(cId));
		//一括更新ドロップダウン用のリスト（選考ステータス、詳細、エージェント、紹介元）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "multiple/update_input";
	}

	/**
	 *
	 * @param cId 候補者ID（配列）
	 * @param muForm 一括更新のフォーム
	 * @param model
	 * @return 一覧画面
	 */
	@PostMapping("multiple")
	public String multipleUpdateComplete(@RequestParam("candidateId") Integer[] cId, @ModelAttribute("multipleUpdateForm") MultipleUpdateForm muForm, Model model) {
		//候補者情報の一括保存、更新
		List<Candidate> cList= candidateService.multipleUpdate(cId, muForm);
		selectionService.multipleUpdate(cList, muForm.getSlcDateString());
		return "redirect:/recruit/candidates";
	}

	/**
	 * 候補者情報の一括削除
	 *
	 * @param cId 候補者ID（配列）
	 * @param model
	 * @return 一覧画面
	 */
	@PostMapping("multiple/delete")
	public String multipleDelete(@RequestParam("candidateId") Integer[] cId, Model model) {
		//候補者情報、選考情報の一括削除
		candidateService.multipleDelete(cId);
		selectionService.multipleDelete(cId);
		return "redirect:/recruit/candidates";
	}
}
