package jp.co.tc.recruit.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.Bean.StatisticsBean;
import jp.co.tc.recruit.Bean.TransitionRateBean;
import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.repository.educational.UniversityRankRepository;
import jp.co.tc.recruit.service.AgentService;
import jp.co.tc.recruit.service.StatisticsService;
import jp.co.tc.recruit.service.V_TransitionPopulationService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	V_TransitionPopulationService transitionPopulationService;
	@Autowired
	StatisticsService statisticsService;
	@Autowired
	AgentService agentService;
	@Autowired
	UniversityRankRepository universityRankRepository;

	private final int COL_NUM = 13;

	@GetMapping
	public String statisticsIndex() {
		return "statistics/statistics_maintenance";
	}

	/**
	 * 選考ステータス分析一覧画面へ遷移
	 * @param model
	 * @return
	 */
	@GetMapping("slcstatuslist")
	public String slcStatusIndex(Model model) {
		Calendar cal = Calendar.getInstance();
		model.addAttribute("year", cal.get(Calendar.YEAR));
		model.addAttribute("month", (cal.get(Calendar.MONTH) + 1));
		//System.out.println("月：" + cal.get(Calendar.MONTH));
		model.addAttribute("statisticList", statisticsService.findBySlcStatusStatic());
		return "statistics/slc_status_analysis_list2";
	}

	/**
	 * 各選考ステータス分析画面に遷移
	 * @param model
	 * @param statisticsId
	 * @param year
	 * @param month
	 * @return
	 */
	@GetMapping("slcstatus/{statisticsId}/{year}/{month}")
	public String slcStatus(Model model, @PathVariable Integer statisticsId, @PathVariable Integer year,
			@PathVariable Integer month) {
		model.addAttribute("slcStatusAnayList", statisticsService.analysisSlcStatus(statisticsId, year, month));
		List<StatisticsBean> statiaticsResult = (List<StatisticsBean>) model.getAttribute("slcStatusAnayList");
		for (StatisticsBean v : statiaticsResult) {
			System.out.println(v.getName() + " " + v.getCount());
		}
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("statistics", statisticsService.findByStatisticsId(statisticsId));
		return "statistics/slc_status_analysis";
	}

	@GetMapping("slcstatus/{statisticsId}/{year}/{monthfirst}/{monthend}")
	public String slcStatusCustom(Model model, @PathVariable Integer statisticsId, @PathVariable Integer year,
			@PathVariable Integer monthfirst, @PathVariable Integer monthend) {
		model.addAttribute("slcStatusAnayList", statisticsService.analysisSlcStatusByMonth(statisticsId, year, monthfirst, monthend));
//		List<StatisticsBean> statiaticsResult = (List<StatisticsBean>) model.getAttribute("slcStatusAnayList");
//		for (StatisticsBean v : statiaticsResult) {
//			System.out.println(v.getName() + " " + v.getCount());
//		}
		System.out.println("表示期間" + monthfirst + "月～" + monthend + "月");
		model.addAttribute("year", year);
		model.addAttribute("monthfirst", monthfirst);
		model.addAttribute("monthend", monthend);
		model.addAttribute("statistics", statisticsService.findByStatisticsId(statisticsId));
		return "statistics/slc_status_analysis2";
	}

	/**
	 * 移行率分析画面に遷移
	 * @param model
	 * @param year
	 * @return
	 */
	@PostMapping("/transitionrate")
	public String showTransitionrate(Model model, Integer year) {
		model.addAttribute("tstRateList", transitionPopulationService.findByStatisticsTransitionrate(year));
		List<String> colName = new ArrayList<>();
		List<String> monthName = new ArrayList<>();
		for (int i = 1; i <= COL_NUM; i++) {
			if ((i + 3) <= 12) {
				monthName.add((i + 3) + "月度");
			} else if ((i - 9) <= 3) {
				monthName.add((i - 9) + "月度");
			} else {
				monthName.add("総計");
			}
		}
		colName.add("人数");
		colName.add("対母集団率");
		colName.add("移行率");
		int count = 0;
		List<List<TransitionRateBean>> transitionRateResult = transitionPopulationService
				.findByStatisticsTransitionrate(2020);
		for (List<TransitionRateBean> v : transitionRateResult) {
			count++;
			System.out.println(count + "月");
			for (TransitionRateBean x : v) {
				System.out.print(x.getRowName());
				System.out.print("　" + x.getHitNum());
				System.out.print("　" + x.getPopulationRate());
				System.out.println("　" + x.getTransitionRate());
			}
		}
		model.addAttribute("colNames", colName);
		model.addAttribute("monthList", monthName);
		model.addAttribute("pers", "%");
		model.addAttribute("year", year);
		return "statistics/transitionrate";
	}

	@PostMapping("/transitionrate/agent")
	public String showTransitionrateByAgent(Model model, Integer year, Integer agentId) {
		model.addAttribute("tstRateList",
				transitionPopulationService.findByStatisticsTransitionrateAgent(year, agentId));
		List<String> colName = new ArrayList<>();
		List<String> monthName = new ArrayList<>();
		for (int i = 1; i <= COL_NUM; i++) {
			if ((i + 3) <= 12) {
				monthName.add((i + 3) + "月度");
			} else if ((i - 9) <= 3) {
				monthName.add((i - 9) + "月度");
			} else {
				monthName.add("総計");
			}
		}
		colName.add("人数");
		colName.add("対母集団率");
		colName.add("移行率");
		if (agentId == null) {
			agentId = agentService.findTop1().getAgentId();
		}
		model.addAttribute("agentId", agentId);
		model.addAttribute("colNames", colName);
		model.addAttribute("monthList", monthName);
		model.addAttribute("pers", "%");
		model.addAttribute("year", year);
		model.addAttribute("agentList", agentService.findByNotDeleted());
		return "statistics/transitionrate_by_agent";
	}

	@PostMapping("/transitionrate/university")
	public String showTransitionrateByUnivaesity(Model model, Integer year, Integer universityRankId) {
		model.addAttribute("tstRateList",
				transitionPopulationService.findByStatisticsTransitionrateUniversityRank(year, universityRankId));
		List<String> colName = new ArrayList<>();
		List<String> monthName = new ArrayList<>();
		for (int i = 1; i <= COL_NUM; i++) {
			if ((i + 3) <= 12) {
				monthName.add((i + 3) + "月度");
			} else if ((i - 9) <= 3) {
				monthName.add((i - 9) + "月度");
			} else {
				monthName.add("総計");
			}
		}
		colName.add("人数");
		colName.add("対母集団率");
		colName.add("移行率");
		if (universityRankId == null) {
			universityRankId = universityRankRepository
					.findFirstByDeleteFlagOrderByUniversityRankId(DeleteFlagConstant.NOT_DELETED).getUniversityRankId();
		}
		model.addAttribute("universityRankId", universityRankId);
		model.addAttribute("colNames", colName);
		model.addAttribute("monthList", monthName);
		model.addAttribute("pers", "%");
		model.addAttribute("year", year);
		model.addAttribute("universityRankList", universityRankRepository.findByDeleteFlagOrderByUniversityRankId(DeleteFlagConstant.NOT_DELETED));
		return "statistics/transitionrate_by_university";
	}
}
