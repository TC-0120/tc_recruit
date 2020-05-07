package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

<<<<<<< HEAD
=======
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.entity.Selection.SelectionPK;
import jp.co.tc.recruit.form.ConditionsForm;
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
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
<<<<<<< HEAD
	public String index(Model model) {
		model.addAttribute("candidates", candidateService.findAll());
		model.addAttribute("selections", selectionService.findAll());
		/*	model.addAttribute("slcStatusList", slcStatusService.findAll());*/
		model.addAttribute("slcStatusDtlList", slcStatusDtlService.findAll());
		return "top";
	}

	/*@GetMapping("filter")
	public String filter(@RequestParam("SlcStatusF") Integer ssId, @RequestParam("SlcStatusDtlF") Integer ssdId, Model model) {
		model.addAttribute("candidates", candidateService.findBySlcStatusIdAndSlcStatudDtlId(ssId, ssdId));
		model.addAttribute("selections", selectionService.findAll());
=======
	public String filter(@ModelAttribute("conditionsForm") ConditionsForm conditionsForm, Model model) {
		model.addAttribute("candidates", candidatesViewService.findBySlcStatusIdAndSlcStatudDtlId(conditionsForm));
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
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
	public String registComplete(@ModelAttribute Candidate candidate) {
		candidateService.save(candidate);
		//SelectionPK slcPK = new SelectionPK(candidate.getCandidateId(), candidate.getSlcStatus().getSlcStatusId());
		//selectionService.save(new Selection(slcPK));
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

		//自動管理
		//マジックナンバー
		if (slcDate.isEmpty() || slcDate == null) {
			candidateService.slcStatusManagement(cId, 1);
		} else {
			candidateService.slcStatusManagement(cId, 2);
		}

		if (slc.getSlcResult() != null) {
			if (slc.getSlcResult() == 0) {
				candidateService.slcStatusManagement(cId, 4);
			} else if (slc.getSlcResult() == 1) {
				candidateService.slcStatusManagement(cId, 3);
				//次のステータスの選考情報を登録
				//selectionService.save(new Selection(new SelectionPK(cId, sId + 1)));
			}
		}
		return "redirect:/recruit/candidates";
	}
	*/

}
