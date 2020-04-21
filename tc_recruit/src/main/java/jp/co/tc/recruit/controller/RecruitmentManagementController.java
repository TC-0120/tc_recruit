package jp.co.tc.recruit.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.entity.Selection.SelectionPK;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidatesViewRepository;
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
	//消す
	@Autowired
	CandidatesViewRepository cvRepo;

	@GetMapping
	public String filter(@ModelAttribute("conditionsForm") ConditionsForm conditionsForm, Model model) {
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

	@GetMapping("{id}")
	public String updateInput(@PathVariable Integer id, Model model) {
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
		candidateService.save(candidate);
		return "redirect:/recruit/candidates";
	}

	@GetMapping("{id}/delete")
	public String delete(@PathVariable Integer id) {
		candidateService.delete(id);
		//selectionService.deleteByCandidateId(id);
		return "redirect:/recruit/candidates";
	}

	@GetMapping("/selection/{cId}/{sId}")
	public String slcDateUpdate(@PathVariable Integer cId, @PathVariable Integer sId, Model model) {
		SelectionPK slcPK = new SelectionPK(cId, sId);
		Selection slc = selectionService.findById(slcPK);
		model.addAttribute("candidate", candidateService.findById(cId));
		model.addAttribute("selection", slc);
		model.addAttribute("slcDateString", selectionService.getStringDate(slc.getSlcDate()));
		return "selection/update_input";
	}

	@PostMapping("/selection/{cId}/{sId}")
	public String update(@PathVariable Integer cId, @PathVariable Integer sId, @ModelAttribute Selection slc, @RequestParam("slcDateString") String slcDate) throws ParseException {
		SelectionPK slcPK = new SelectionPK(cId, sId);
		slc.setSlcPK(slcPK);
		slc.setSlcDate(selectionService.setDate(slcDate));
		selectionService.save(slc);
		candidateService.slcStatusManagement(cId, slcDate, slc.getSlcResult());

		//自動管理
		//マジックナンバー
		//if (slcDate.isEmpty() || slcDate == null) {
		//	candidateService.slcStatusManagement(cId, 1);
		//} else {
		//	candidateService.slcStatusManagement(cId, 2);
		//}
        //
		//if (slc.getSlcResult() != null) {
		//	if (slc.getSlcResult() == 0) {
		//		candidateService.slcStatusManagement(cId, 4);
		//	} else if (slc.getSlcResult() == 1) {
		//		candidateService.slcStatusManagement(cId, 3);
		//	}
		//}
		return "redirect:/recruit/candidates";
	}


}
