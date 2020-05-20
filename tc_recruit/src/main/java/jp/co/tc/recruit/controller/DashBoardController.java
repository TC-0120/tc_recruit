package jp.co.tc.recruit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.LatestPlanView;
import jp.co.tc.recruit.entity.TotalCheckView;
import jp.co.tc.recruit.entity.TotalSelectionView;
import jp.co.tc.recruit.service.CandidateService;
import jp.co.tc.recruit.service.LatestPlanService;
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
	@Autowired
	LatestPlanService ltsPlnSvc;
	@Autowired
	CandidateService cddSvc;

	@GetMapping("/dashboard")
	public String getDashBoard(Model model) {
		/*選考中候補者集計*/
		Integer sttMsgId;
		Integer ttlSlcCount = 0;
		TotalSelectionView ttlSlc;
		List<TotalSelectionView> ttlSlcList = new ArrayList<TotalSelectionView>();
		/*選考中(ALL)のみ集計値がDBにないので先に取り出し*/
		TotalSelectionView ttlSlcAll = ttlSlcSvc.findByStatusMessageId(1);
		for (sttMsgId = 2; sttMsgId <= 9; sttMsgId++) {
			ttlSlc = ttlSlcSvc.findByStatusMessageId(sttMsgId);
			ttlSlcList.add(ttlSlc);
			ttlSlcCount += ttlSlc.getCount();
		}
		/*適性検査未受検者はcandedateテーブルのappitude_flg(=0)から集計*/
		List<Candidate> candidateAll = cddSvc.findAll();
		Integer aptFlgCount = 0;
		for (Integer cddId = 1; cddId <= candidateAll.size(); cddId++) {
			if (cddSvc.findById(cddId).getAptitudeFlag() == 0) {
				aptFlgCount++;
			}
		}

		/*選考中(ALL)ステータス名称用*/
		model.addAttribute("ttlSlcAll", ttlSlcAll);
		/*選考中(ALL)の候補者全数*/
		model.addAttribute("ttlSlcCount", ttlSlcCount);
		/*その他ステータス名称と集計値*/
		model.addAttribute("ttlSlcList", ttlSlcList);
		/*適性検査完了者集計値*/
		model.addAttribute("aptFlgCount", aptFlgCount);

		/*要対応事項集計*/
		Integer ttlChkCount = 0;
		TotalCheckView ttlChk;
		List<TotalCheckView> ttlChkList = new ArrayList<TotalCheckView>();
		/*要対応(ALL)のみ集計値がDBにないので先に取り出し*/
		TotalCheckView ttlChkAll = ttlChkSvc.findByStatusMessageId(10);
		for (sttMsgId = 11; sttMsgId <= 26; sttMsgId++) {
			ttlChk = ttlChkSvc.findByStatusMessageId(sttMsgId);
			ttlChkList.add(ttlChk);
			/*要対応(ALL)の集計：合否判定、承諾待ち、確定はtotal_over_date
			                     それ以外はtotal_countの値を足し合わせる*/
			if ((ttlChk.getSelectionStatusId() == 1 && ttlChk.getSelectionStatusDetailId() == 2)
					|| (ttlChk.getSelectionStatusId() == 2 && ttlChk.getSelectionStatusDetailId() == 2)
					|| (ttlChk.getSelectionStatusId() == 3 && ttlChk.getSelectionStatusDetailId() == 2)
					|| (ttlChk.getSelectionStatusId() == 4 && ttlChk.getSelectionStatusDetailId() == 2)
					|| (ttlChk.getSelectionStatusId() == 5 && ttlChk.getSelectionStatusDetailId() == 2)
					|| (ttlChk.getSelectionStatusId() == 6 && ttlChk.getSelectionStatusDetailId() == 6)
					|| (ttlChk.getSelectionStatusId() == 7 && ttlChk.getSelectionStatusDetailId() == 8)) {
				ttlChkCount += ttlChk.getTotalOverDate();
			} else {
				ttlChkCount += ttlChk.getTotalCount();
			}
		}
		/*要対応(ALL)ステータス名称用*/
		model.addAttribute("ttlChkAll", ttlChkAll);
		/*要対応(ALL)の全数*/
		model.addAttribute("ttlChkCount", ttlChkCount);
		/*その他ステータス名称と集計値*/
		model.addAttribute("ttlChkList", ttlChkList);
		/*選考ステータス詳細が選考中,承諾待ち,確定(詳細ID=2||6||8)の場合
		 * 今日の日付を追加送信するためtodayを格納*/
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		model.addAttribute("today", today);

		/*タスク別の集計*/
		Integer ttlScheduleCount = 0;
		Integer ttlAssessmentCount = 0;
		Integer ttlUnreportCount = 0;
		TotalCheckView ttlTskChk;
		for (sttMsgId = 11; sttMsgId <= 26; sttMsgId++) {
			ttlTskChk = ttlChkSvc.findByStatusMessageId(sttMsgId);
			/*メッセージステータスが日程調整,合否判定,合格未通知
			 * (total_check_viewの管理下で、詳細ID=1||2||3)の場合、それぞれ集計*/
			if (ttlTskChk.getSelectionStatusDetailId() == 1) {
				ttlScheduleCount += ttlTskChk.getTotalCount();
			} else if (ttlTskChk.getSelectionStatusDetailId() == 2) {
				ttlAssessmentCount += ttlTskChk.getTotalOverDate();
			} else if (ttlTskChk.getSelectionStatusDetailId() == 3) {
				ttlUnreportCount += ttlTskChk.getTotalCount();
			}
		}
		/*タスク別集計のステータス名称*/
		String[] statusByTskType = { "日程調整", "合否判定", "合格未通知" };
		model.addAttribute("statusByTskType", statusByTskType);
		/*タスク別の集計値*/
		Integer[] ttlCountByTskType = { ttlScheduleCount, ttlAssessmentCount, ttlUnreportCount };
		model.addAttribute("ttlCountByTskType", ttlCountByTskType);

		/*今日明日のタスク*/
		boolean result = true;
		List<LatestPlanView> ltsPlnList = new ArrayList<LatestPlanView>();
		ltsPlnList = ltsPlnSvc.findAll();
		if (CollectionUtils.isEmpty(ltsPlnList)) {
			result = false;
		}
		/*今日明日のタスクリスト*/
		model.addAttribute("ltsPlnList", ltsPlnList);
		/*今日明日のタスクリストEmptyか否か*/
		model.addAttribute("result", result);
		/*今日明日の面接予定者一覧ボタン押下で
		 * 明日の日付を送信するためtomorrowを格納*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		String tomorrow = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());
		model.addAttribute("tomorrow", tomorrow);

		return "dashboard";
	}

	/*@PostMapping("/dashboard")
	public String postLogin(Model model) {

	    return "redirect:/";
	}*/

}
