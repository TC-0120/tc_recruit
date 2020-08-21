package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.Bean.StatisticsBean;
import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.statistics.Statistics;
import jp.co.tc.recruit.entity.view.V_TransitionPopulation;
import jp.co.tc.recruit.repository.StatisticsConfigRepository;
import jp.co.tc.recruit.repository.StatisticsRepository;
import jp.co.tc.recruit.repository.view.V_TransitionPopulationRepository;

/**
 * 統計設定テーブル用サービスクラス
 * @author TC-0120
 *
 */
@Service
public class StatisticsService {
	@Autowired
	StatisticsRepository statisticsRepository;
	@Autowired
	V_TransitionPopulationRepository transitionPopulationRepository;
	@Autowired
	StatisticsConfigRepository statisticsConfigRepository;
	/**
	 * 説明会
	 */
	private final int INFORMATION_SESSION = 1;
	/**
	 * 書類選考
	 */
	private final int APPLICANT_SCREENING = 2;
	/**
	 * 一次面接
	 */
	private final int FIRST_INTERVIEW = 3;
	/**
	 * 二次面接
	 */
	private final int SECOND_INTERVIEW = 4;
	/**
	 * 最終面接
	 */
	private final int LAST_INTERVIEW = 5;
	/**
	 * 内定
	 */
	private final int INFORMAL_OFFER = 6;
	/**
	 * 入社手続き
	 */
	private final int JOINNING_PROCEDURE = 7;
	//選考ステータス分析
	private final int SLC_STATUS_STATIC = 1;

	//内定確定者数
	private int informalOffers;
	//内定辞退OR応諾待ち数
	private int informalOfferFailures;
	//
	private int informalOfferRefusals;
	//最終面接脱落者数
	private int lastInterviewFailures;
	//最終面接辞退者数
	private int lastInterviewRefusals;
	//二次面接脱落者数
	private int secondInterviewFailures;
	//二次面接辞退者数
	private int secondInterviewRefusals;
	//一次面接不合格者数
	private int firstInterviewFailures;
	//一次面接辞退者数
	private int firstInterviewRefusals;
	//書類選考合格者数
	private int applicantScreeningPassing;
	//書類選考不合格数
	private int applicantScreeningRefusals;
	//書類選考不合格OR辞退者数
	private int applicantScreeningFailure;
	//説明会合格者数
	private int informationSessionPassing;
	//説明会不合格者数
	private int informationSessionRefusals;
	//説明会不合格OR辞退者数
	private int informationSessionFailures;

	//全件数
	private int hitAll;
	//それぞれの該当者数
	//内定者数
	private int informalOfferCounts;
	//二次面接合格者数
	private int secondInterviewCounts;
	//一次面接合格者数
	private int firstInterviewPassingCounts;
	//一次面接希望者数
	private int firstInterviewCounts;
	//説明会・書類選考合格
	private int apScePassingCounts;

	public List<Statistics> findAll() {
		return statisticsRepository.findByDeleteFlagOrderByStatisticsId(DeleteFlagConstant.NOT_DELETED);
	}

	public List<Statistics> findBySlcStatusStatic() {
		return statisticsRepository.findByStatisticsConfigAndDeleteFlagOrderByStatisticsId(statisticsConfigRepository
				.findByStatisticsConfigIdAndDeleteFlag(SLC_STATUS_STATIC, DeleteFlagConstant.NOT_DELETED),
				DeleteFlagConstant.NOT_DELETED);
	}

	public Statistics findByStatisticsId(Integer statisticsId) {
		return statisticsRepository.findByStatisticsIdAndDeleteFlag(statisticsId, DeleteFlagConstant.NOT_DELETED);
	}

	/**
	 * 選考ステータスごとの分析
	 * @param statisticsId
	 * @param year
	 * @param month
	 * @return
	 */
	public List<StatisticsBean> analysisSlcStatus(Integer statisticsId, Integer year, Integer month) {
		List<V_TransitionPopulation> statisticsSlcStatus = transitionPopulationRepository
				.findByInsertYearAndInsertMonthAndSlcStatusId(year, month);
		setCount(statisticsSlcStatus);
		Statistics statistics = statisticsRepository.findByStatisticsIdAndDeleteFlag(statisticsId,
				DeleteFlagConstant.NOT_DELETED);
		return countExecuteBySlcStatus(statistics.getSelectionStatusId());
	}

	public List<List<StatisticsBean>> analysisSlcStatusByMonth(Integer statisticsId, Integer year, Integer monthfirst,
			Integer monthend) {
		return sortBymonth(statisticsId, year, monthfirst, monthend);
	}

	public List<List<StatisticsBean>> sortBymonth(Integer statisticsId, Integer year, Integer monthfirst, Integer monthend) {
		List<List<StatisticsBean>> statiaticsResult = new ArrayList<>();
		Statistics statistics = statisticsRepository.findByStatisticsIdAndDeleteFlag(statisticsId,
				DeleteFlagConstant.NOT_DELETED);
		if (V_TransitionPopulationService.APRIL <= monthfirst && monthfirst <= V_TransitionPopulationService.DECEMBER) {

			if (V_TransitionPopulationService.JANUARY <= monthend && monthend <= V_TransitionPopulationService.MARCH) {
				for (int month = monthfirst; month <= V_TransitionPopulationService.DECEMBER; month++) {
					setCount(transitionPopulationRepository.findByInsertYearAndInsertMonthAndSlcStatusId(year, month));
					statiaticsResult.add(countExecuteBySlcStatus(statistics.getSelectionStatusId()));
				}
				for (int month = V_TransitionPopulationService.JANUARY; month <= monthend; month++) {
					setCount(transitionPopulationRepository.findByInsertYearBetweenInsertMonthAndSlcStatusId(year, month));
					statiaticsResult.add(countExecuteBySlcStatus(statistics.getSelectionStatusId()));
				}
			} else {
				for(int month = monthfirst; month <= monthend ; month++) {
					setCount(transitionPopulationRepository.findByInsertYearAndInsertMonthAndSlcStatusId(year, month));
					statiaticsResult.add(countExecuteBySlcStatus(statistics.getSelectionStatusId()));
				}
			}
		}else {
			for(int month = monthfirst; month <= monthend ; month++) {
				setCount(transitionPopulationRepository.findByInsertYearBetweenInsertMonthAndSlcStatusId(year, month));
				statiaticsResult.add(countExecuteBySlcStatus(statistics.getSelectionStatusId()));
			}
		}
		return statiaticsResult;
	}

	public void setCount(List<V_TransitionPopulation> transitionPopulationByMonth) {
		//内定確定者数
		informalOffers = V_TransitionPopulationService.countInformalOffer(transitionPopulationByMonth);
		//内定辞退OR応諾待ち数
		informalOfferFailures = V_TransitionPopulationService
				.countInformalOfferFailure(transitionPopulationByMonth);
		//最終面接脱落者数
		lastInterviewFailures = V_TransitionPopulationService
				.countLastInterviewFailure(transitionPopulationByMonth);
		//二次面接脱落者数
		secondInterviewFailures = V_TransitionPopulationService
				.countSecondInterviewFailure(transitionPopulationByMonth);
		//一次面接脱落者数
		firstInterviewFailures = V_TransitionPopulationService
				.countFirstInterviewFailure(transitionPopulationByMonth);
		//書類選考不合格者数
		applicantScreeningFailure = V_TransitionPopulationService
				.countApplicantScreeningFailure(transitionPopulationByMonth);
		//説明会不合格者数
		informationSessionFailures = V_TransitionPopulationService
				.countInformationSessionFailure(transitionPopulationByMonth);
		//一次面接辞退者数
		firstInterviewRefusals = V_TransitionPopulationService.countFirstInterviewRefusal(transitionPopulationByMonth)
				- firstInterviewFailures;
		//二次面接辞退者数
		secondInterviewRefusals = V_TransitionPopulationService
				.countSecondInterviewRefusal(transitionPopulationByMonth);
		//最終面接辞退者数
		lastInterviewRefusals = V_TransitionPopulationService.countLastInterviewRefusal(transitionPopulationByMonth);
		//内定辞退者数を計算
		informalOfferRefusals = V_TransitionPopulationService.countInformalOfferRefusal(transitionPopulationByMonth);
		//説明会合格者数
		informationSessionPassing = V_TransitionPopulationService
				.countInformationSessionPassing(transitionPopulationByMonth);
		//書類選考合格者数
		applicantScreeningPassing = V_TransitionPopulationService
				.countApplicantScreeningPassing(transitionPopulationByMonth);
		//書類選考不合格数
		applicantScreeningRefusals = V_TransitionPopulationService
				.countApplicantScreeningRefusal(transitionPopulationByMonth);
		//説明会不合格数
		informationSessionRefusals = V_TransitionPopulationService
				.countInformationSessionRefusal(transitionPopulationByMonth);

		//全件数
		hitAll = transitionPopulationByMonth.size();
		//それぞれの該当者数
		//内定者数
		informalOfferCounts = informalOffers + informalOfferFailures;
		//二次面接合格者数
		secondInterviewCounts = informalOfferCounts + lastInterviewFailures
				+ V_TransitionPopulationService.countSecondInterviewPassing(transitionPopulationByMonth);
		//一次面接合格者数
		firstInterviewPassingCounts = secondInterviewCounts + secondInterviewFailures
				+ V_TransitionPopulationService.countFirstInterviewPassing(transitionPopulationByMonth);
		//一次面接希望者数
		firstInterviewCounts = firstInterviewPassingCounts
				+ V_TransitionPopulationService.countFirstInterviewFailure(transitionPopulationByMonth)
				+ V_TransitionPopulationService.countFirstInterviewSelecting(transitionPopulationByMonth);
		//説明会・書類選考合格
		apScePassingCounts = hitAll - (applicantScreeningFailure + informationSessionFailures);
	}

	public List<StatisticsBean> countExecuteBySlcStatus(Integer slcStatusId) {
		List<StatisticsBean> statiaticsResult = new ArrayList<>();
		switch (slcStatusId) {
		case INFORMATION_SESSION:
			statiaticsResult = countByInformationSession();
			break;
		case APPLICANT_SCREENING:
			statiaticsResult = countByApplicantScreening();
			break;
		case FIRST_INTERVIEW:
			statiaticsResult = countByFirstInterview();
			break;
		case SECOND_INTERVIEW:
			statiaticsResult = countBySecondInterview();
			break;
		case LAST_INTERVIEW:
			statiaticsResult = countByLastInterview();
			break;
		case INFORMAL_OFFER:
			statiaticsResult = countByInformalOffer();
			break;
		}
		return statiaticsResult;
	}

	/**
	 *書類選考結果集計
	 * @return
	 */
	public List<StatisticsBean> countByInformationSession() {
		List<StatisticsBean> informationSession = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(apScePassingCounts - applicantScreeningPassing);
		passing.setName("合格");
		informationSession.add(passing);
		StatisticsBean failure = new StatisticsBean();
		failure.setCount(informationSessionRefusals);
		failure.setName("不合格");
		informationSession.add(failure);

		return informationSession;
	}

	/**
	 *書類選考結果集計
	 * @return
	 */
	public List<StatisticsBean> countByApplicantScreening() {
		List<StatisticsBean> applicantScreening = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(apScePassingCounts - informationSessionPassing);
		passing.setName("合格");
		applicantScreening.add(passing);
		StatisticsBean failure = new StatisticsBean();
		failure.setCount(applicantScreeningRefusals);
		failure.setName("不合格");
		applicantScreening.add(failure);

		return applicantScreening;
	}

	/**
	 * 一次面接結果集計
	 * @return
	 */
	public List<StatisticsBean> countByFirstInterview() {
		List<StatisticsBean> firstInterview = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(informalOfferCounts);
		passing.setName("合格");
		firstInterview.add(passing);
		StatisticsBean failure = new StatisticsBean();
		failure.setCount(firstInterviewFailures);
		failure.setName("不合格");
		firstInterview.add(failure);
		StatisticsBean refusal = new StatisticsBean();
		refusal.setCount(firstInterviewRefusals);
		refusal.setName("辞退");
		firstInterview.add(refusal);

		return firstInterview;
	}

	/**
	 *二次面接結果集計
	 * @return
	 */
	public List<StatisticsBean> countBySecondInterview() {
		List<StatisticsBean> secondInterview = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(secondInterviewCounts);
		passing.setName("合格");
		secondInterview.add(passing);
		StatisticsBean failure = new StatisticsBean();
		failure.setCount(secondInterviewFailures - secondInterviewRefusals);
		failure.setName("不合格");
		secondInterview.add(failure);
		StatisticsBean refusal = new StatisticsBean();
		refusal.setCount(secondInterviewRefusals);
		refusal.setName("辞退");
		secondInterview.add(refusal);

		return secondInterview;
	}

	/**
	 * 最終面接集計結果
	 * @return
	 */
	public List<StatisticsBean> countByLastInterview() {
		List<StatisticsBean> lastInterview = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(informalOfferCounts);
		passing.setName("合格");
		lastInterview.add(passing);
		StatisticsBean failure = new StatisticsBean();
		failure.setCount(lastInterviewFailures - lastInterviewRefusals);
		failure.setName("不合格");
		lastInterview.add(failure);
		StatisticsBean refusal = new StatisticsBean();
		refusal.setCount(lastInterviewRefusals);
		refusal.setName("辞退");
		lastInterview.add(refusal);

		return lastInterview;
	}

	/**
	 * 内定状況集計結果
	 * @return
	 */
	public List<StatisticsBean> countByInformalOffer() {
		List<StatisticsBean> informalOffer = new ArrayList<>();
		StatisticsBean passing = new StatisticsBean();
		passing.setCount(informalOffers);
		passing.setName("内定承諾");
		informalOffer.add(passing);
		StatisticsBean refusal = new StatisticsBean();
		refusal.setCount(informalOfferRefusals);
		refusal.setName("内定辞退");
		informalOffer.add(refusal);

		return informalOffer;
	}
}
