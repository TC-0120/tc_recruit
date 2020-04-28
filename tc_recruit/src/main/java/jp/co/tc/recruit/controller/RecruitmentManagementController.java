package jp.co.tc.recruit.controller;

import java.text.ParseException;
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

	@GetMapping
	public String index(@ModelAttribute("conditionsForm") ConditionsForm conditionsForm, Model model) {
		model.addAttribute("candidates", candidatesViewService.findBySlcStatusIdAndSlcStatudDtlId(conditionsForm));
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		return "top";
	}

	@GetMapping("regist")
	public String registInput(Model model) {
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "candidate/regist_input";
	}

	@PostMapping
	public String registComplete(@ModelAttribute Candidate candidate, @RequestParam("slcDate") String slcDate) {
		candidateService.regist(candidate, slcDate);
		selectionService.regist(candidate.getCandidateId(), candidate.getSlcStatus().getSlcStatusId(), slcDate);
		return "redirect:/recruit/candidates";
	}

	@GetMapping("update")
	public String updateInput(@RequestParam("candidateId") Integer id, Model model) {
		model.addAttribute("candidate", candidateService.findById(id));
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "candidate/update_input";
	}

	@PostMapping("{id}")
	public String updateComplete(@PathVariable Integer id, @ModelAttribute Candidate candidate) {
		candidate.setCandidateId(id);
		candidateService.update(candidate);
		return "redirect:/recruit/candidates";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Integer id) {
		candidateService.delete(id);
		selectionService.deleteByCandidateId(id);
		return "redirect:/recruit/candidates";
	}

	@GetMapping("selection")
	public String slcUpdateInput(@RequestParam("candidateId") Integer cId, Model model) {
		//候補者IDから候補者情報、選考情報を取得
		Candidate candidate = candidateService.findById(cId);
		Integer sId = candidate.getSlcStatus().getSlcStatusId();
		SelectionPK slcPK = new SelectionPK(cId, sId);
		Selection slc = selectionService.findById(slcPK);

		model.addAttribute("candidate", candidate);
		model.addAttribute("selection", slc);
		model.addAttribute("slcDateString", selectionService.getStringDate(slc.getSlcDate()));
		model.addAttribute("slcResult", candidate.getSlcStatusDtl().getSlcStatusDtlId());
		return "selection/update_input";
	}


	@PostMapping("selection")
	public String slcUpdateComplete(@RequestParam("slcResult") Integer slcResult, @ModelAttribute Selection slc, @RequestParam("slcDateString") String slcDate) throws ParseException {
		slc.setSlcDate(selectionService.setDate(slcDate));
		selectionService.save(slc);
		candidateService.slcStatusManagement(slc.getSlcPK().getCandidateId(), slcResult, slcDate);

		return "redirect:/recruit/candidates";
	}

	@PostMapping("seleciton/nextStatus")
	public String nextStatus(@RequestParam("candidateId") Integer cId, RedirectAttributes redirectAttributes) {
		if (candidateService.slcStatusUp(cId)) {
			redirectAttributes.addAttribute("candidateId", cId);
			return "redirect:/recruit/candidates/selection";
		} else {
			return "redirect:/recruit/candidates";
		}
	}

	@GetMapping("multiple")
	public String multipleUpdate(@RequestParam("candidateId") Integer[] cId, @ModelAttribute("multipleUpdateForm") MultipleUpdateForm muForm, Model model) {
		model.addAttribute("candidates", candidatesViewService.getCandidatesByCandidateId(cId));
		model.addAttribute("slcStatusList", slcStatusService.findAll());
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		model.addAttribute("agentList", agentService.findAll());
		model.addAttribute("referrerList", referrerService.findAll());
		return "multiple/update_input";
	}

	@PostMapping("multiple")
	public String multipleUpdateComplete(@RequestParam("candidateId") Integer[] cId, @ModelAttribute("multipleUpdateForm") MultipleUpdateForm muForm, Model model) {
		List<Candidate> cList= candidateService.multipleUpdate(cId, muForm);
		selectionService.multipleUpdate(cList, muForm.getSlcDateString());
		return "redirect:/recruit/candidates";
	}

	@PostMapping("multiple/delete")
	public String multipleDelete(@RequestParam("candidateId") Integer[] cId, Model model) {
		candidateService.multipleDelete(cId);
		selectionService.multipleDelete(cId);
		return "redirect:/recruit/candidates";
	}
}
