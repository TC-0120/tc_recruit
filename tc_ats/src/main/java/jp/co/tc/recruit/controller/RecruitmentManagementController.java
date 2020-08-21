package jp.co.tc.recruit.controller;

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
import jp.co.tc.recruit.entity.selection.Selection;
import jp.co.tc.recruit.entity.selection.Selection.SelectionPK;
import jp.co.tc.recruit.form.CandidateForm;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidateListRepository;
import jp.co.tc.recruit.service.AgentService;
import jp.co.tc.recruit.service.AptitudeService;
import jp.co.tc.recruit.service.CandidateService;
import jp.co.tc.recruit.service.CandidatesViewService;
import jp.co.tc.recruit.service.SelectionReferrerService;
import jp.co.tc.recruit.service.SelectionService;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.service.educational.DepartmentService;
import jp.co.tc.recruit.service.educational.FacultyService;
import jp.co.tc.recruit.service.educational.UniversityRankService;
import jp.co.tc.recruit.service.educational.UniversityService;
import jp.co.tc.recruit.util.BeanCopy;
import jp.co.tc.recruit.util.DateFormatter;

/**
 * 採用情報管理機能のコントローラー
 *
 * @author TC-0120
 *
 */
@Controller
@RequestMapping("/recruit/candidates")
public class RecruitmentManagementController {

	@Autowired
	CandidatesViewService candidatesViewService;
	@Autowired
	CandidateListRepository candidateListRepository;
	@Autowired
	SelectionReferrerService selectionReferrerService;
	@Autowired
	SelectionStatusService slcStatusService;
	@Autowired
	SelectionStatusDetailService slcStatusDtlService;
	@Autowired
	AgentService agentService;
	@Autowired
	UniversityService universityService;
	@Autowired
	FacultyService facultyService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	UniversityRankService universityRankService;
	@Autowired
	AptitudeService aptitudeService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	SelectionService selectionService;

	//候補者画面マスタID(固定)
	private final int candidateListId = 1;

	/**
	 * 候補者一覧画面での候補者情報の検索、表示
	 *
	 * @param conditionsForm 検索条件
	 * @param model
	 * @return 一覧画面
	 */
	@GetMapping
	public String index(@ModelAttribute("conditionsForm") ConditionsForm conditionsForm, Model model) {//
		//入力された検索条件から候補者情報を取得、格納
		model.addAttribute("candidates",
				candidatesViewService.findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(conditionsForm));
		//検索ドロップダウン用のリスト（選考ステータス、詳細）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("slcReferrerList", selectionReferrerService.findAll());
		model.addAttribute("candidateList", candidateListRepository.getOne(candidateListId));
		return "recruitment_management";
	}

	/**
	 * 候補者情報登録入力画面への遷移
	 *
	 * @param model
	 * @return 候補者情報登録入力画面
	 */
	@GetMapping("register")
	public String transitionToCandidateRegisterInputScreen(Model model) {
		//入力ドロップダウン用のリスト（採用エージェント、紹介元）を格納
		model.addAttribute("agentList", agentService.findAll());
		//入力ドロップダウン用のリスト(大学マスタ）を格納
		model.addAttribute("universityList", universityService.findAll());
		//入力ドロップダウン用のリスト（学部マスタ）を格納
		model.addAttribute("facultyList", facultyService.findAll());
		//入力ドロップダウン用のリスト（学科マスタ）を格納
		model.addAttribute("departmentList", departmentService.findAll());
		//入力ドロップダウン用のリスト（大学ランクマスタ）を格納
		model.addAttribute("universityRankList", universityRankService.findAll());
		//入力ドロップダウン用のリスト（適性検査）を格納
		model.addAttribute("aptitudeList", aptitudeService.findAll());
		return "candidate/register_input";
	}

	/**
	 * 候補者情報を登録
	 *07/14 鶴 フォームクラスの導入
	 *
	 * @param candidate 候補者情報
	 * @param slcDate 選考日程
	 * @return 候補者情報登録入力画面
	 */
	@PostMapping
	public String registerCandidate(@ModelAttribute CandidateForm candidateForm,
			@RequestParam("slcDate") String slcDate) {
		//候補者情報をエンティティにコピー
		Candidate candidate = BeanCopy.copyFormToEntity(candidateForm);
		//候補者情報を登録
		candidateService.register(candidate, slcDate);
		//選考情報を登録
		selectionService.register(candidate.getCandidateId(), candidate.getSlcStatus().getSlcStatusId(), slcDate);
		return "redirect:/recruit/candidates/register";
	}

	/**
	 * 候補者情報変更入力画面への遷移
	 *
	 * @param id 候補者ID
	 * @param model
	 * @return 候補者情報変更入力画面
	 */
	@GetMapping("update/{candidateId}")
	public String transitionToCandidateUpdateInputScreen(@PathVariable("candidateId") Integer cId, Model model) {
		//候補者IDから候補者情報を取得、格納
		model.addAttribute("candidate", candidateService.findById(cId));
		//入力ドロップダウン用のリスト（採用エージェント、紹介元）を格納
		model.addAttribute("agentList", agentService.findAll());
		//入力ドロップダウン用のリスト(大学マスタ）を格納
		model.addAttribute("universityList", universityService.findAll());
		//入力ドロップダウン用のリスト（学部マスタ）を格納
		model.addAttribute("facultyList", facultyService.findAll());
		//入力ドロップダウン用のリスト（学科マスタ）を格納
		model.addAttribute("departmentList", departmentService.findAll());
		//入力ドロップダウン用のリスト（大学ランクマスタ）を格納
		model.addAttribute("universityRankList", universityRankService.findAll());
		//入力ドロップダウン用のリスト（適性検査）を格納
		model.addAttribute("aptitudeList", aptitudeService.findAll());
		//検索ドロップダウン用のリスト（選考ステータス、詳細）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("slcReferrerList", selectionReferrerService.findAll());
		model.addAttribute("slcDate",
				selectionService.updatePrepare(cId, candidateService.findById(cId).getSlcStatus().getSlcStatusId()));
		model.addAttribute("selectionList", selectionService.findBycandidateId(cId));
		//System.out.println("選考日程リスト" + selectionService.findBycandidateId(cId).size());
		return "candidate/update_input";
	}

	/**
	 * 候補者情報を変更
	 *
	 *
	 * @param id 候補者ID
	 * @param candidate 候補者情報
	 * @return 候補者情報変更入力画面
	 */
	@PostMapping("update")
	public String updateCandidate(@ModelAttribute CandidateForm candidateForm, @RequestParam("slcDate") String slcDate,
			RedirectAttributes redirectAttributes) {
		//候補者情報をエンティティにコピー
		Candidate candidate = BeanCopy.copyFormToEntity(candidateForm);
		//候補者情報を変更
		candidateService.update(candidate);
		//選考情報を登録
		selectionService.update(candidate.getCandidateId(), candidate.getSlcStatus().getSlcStatusId(), slcDate);
		redirectAttributes.addAttribute("candidateId", candidate.getCandidateId());
		return "redirect:/recruit/candidates/update/" + candidate.getCandidateId();
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
	public String transitionToSlcInfoUpdateInputScreen(@RequestParam("candidateId") Integer cId, Model model) {
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
		model.addAttribute("slcDateString", DateFormatter.toString(slc.getSlcDate()));
		//選考結果を候補者情報から取得、格納
		model.addAttribute("slcResult", candidate.getSlcStatusDtl().getSlcStatusDtlId());
		//検索ドロップダウン用のリスト（選考ステータス、詳細）を格納
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("slcReferrerList", selectionReferrerService.findAll());

		// 日程が登録された候補者情報を取得、格納
		//model.addAttribute("candidatesHasSlcDate", candidatesViewService.findByIsRegisteredSlcDate());

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
	public String updateSlcInfo(@RequestParam("slcResult") Integer slcResult, @ModelAttribute Selection slc,
			@RequestParam("slcDateString") String slcDateString, RedirectAttributes redirectAttributes, Integer slcStatus) {
		//System.out.println("登録ステータス:" + slcStatus);
		//選考情報を変更
		selectionService.update(slc.getSlcPK().getCandidateId(), slcStatus ,slcDateString);
		//選考ステータスを変更
		candidateService.updateSlcStatusDtl(slc.getSlcPK().getCandidateId(), slcResult, slcDateString);
		redirectAttributes.addAttribute("candidateId", slc.getSlcPK().getCandidateId());
		return "redirect:/recruit/candidates/selection";
	}

	/**
	 * 選考ステータスの繰り上げ
	 *
	 * @param cId 候補者ID
	 * @param redirectAttributes
	 * @return 選考情報変更画面
	 */
	@PostMapping("seleciton/nextStatus")
	public String promoteSlcStatus(@RequestParam("candidateId") Integer cId, RedirectAttributes redirectAttributes) {
		//選考ステータスを繰り上げる
		candidateService.promoteSlcStatus(cId);
		//選考情報変更画面に遷移
		redirectAttributes.addAttribute("candidateId", cId);
		return "redirect:/recruit/candidates/selection";
	}

}
