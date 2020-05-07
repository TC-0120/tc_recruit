package jp.co.tc.recruit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.entity.TotalCheckView;
import jp.co.tc.recruit.entity.TotalSelectionView;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.TotalCheckService;
import jp.co.tc.recruit.service.TotalSelectionService;

@Controller
public class DashBoardController {

	@Autowired
	TotalSelectionService ttlSlcSvc;
	@Autowired
	TotalCheckService ttlChkSvc;
	@Autowired
	MessageStatusService msgSttSvc;

	@GetMapping("/dashboard")
	public String getDashBoard(Model model) {
		/*選考中候補者集計*/
		Integer sttMsgId;
		Integer ttlSlcCount = 0;
		TotalSelectionView ttlSlc;
		List<TotalSelectionView> ttlSlcList = new ArrayList<TotalSelectionView>();
		/*選考中(ALL)のみ集計値がDBにないので先に取り出し*/
		TotalSelectionView ttlSlcAll = ttlSlcSvc.findByStatusMessageId(1);
		for (sttMsgId = 2; sttMsgId < 9; sttMsgId++) {
			ttlSlc = ttlSlcSvc.findByStatusMessageId(sttMsgId);
			ttlSlcList.add(ttlSlc);
			ttlSlcCount += ttlSlc.getCount();
		}
		/*選考中(ALL)*/
		model.addAttribute("ttlSlcAll", ttlSlcAll);
		/*選考中(ALL)の候補者全数*/
		model.addAttribute("ttlSlcCount", ttlSlcCount);
		/*その他ステータスと集計値*/
		model.addAttribute("ttlSlcList", ttlSlcList);

		/*要対応事項集計*/
		Integer ttlChkCount = 0;
		TotalCheckView ttlChk;
		List<TotalCheckView> ttlChkList = new ArrayList<TotalCheckView>();
		/*要対応(ALL)のみ集計値がDBにないので先に取り出し*/
		TotalCheckView ttlChkAll = ttlChkSvc.findByStatusMessageId(9);
		for (sttMsgId = 10; sttMsgId < 19; sttMsgId++) {
			ttlChk = ttlChkSvc.findByStatusMessageId(sttMsgId);
			ttlChkList.add(ttlChk);
			/*要対応(ALL)の集計：合否判定はtotal_except_assessment
			                     それ以外はtotal_assessmentの値を足し合わせる*/
			if (sttMsgId == 11 || sttMsgId == 14 || sttMsgId == 16 || sttMsgId == 18)
				ttlChkCount += ttlChk.getTotalExceptAssessment();
			else {
				ttlChkCount += ttlChk.getTotalAssessment();
			}
		}
		/*要対応(ALL)*/
		model.addAttribute("ttlChkAll", ttlChkAll);
		/*要対応(ALL)の全数*/
		model.addAttribute("ttlChkCount", ttlChkCount);
		/*その他ステータスと集計値*/
		model.addAttribute("ttlChkList", ttlChkList);

		return "dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/

}
