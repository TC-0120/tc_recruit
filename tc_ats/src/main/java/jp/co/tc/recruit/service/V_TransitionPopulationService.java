package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.Bean.TransitionRateBean;
import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.view.V_TransitionPopulation;
import jp.co.tc.recruit.repository.AgentRepository;
import jp.co.tc.recruit.repository.educational.UniversityRankRepository;
import jp.co.tc.recruit.repository.view.V_TransitionPopulationRepository;

/**
 *
 * @author TC-0120
 *統計分析ビューのサービスクラス
 */
@Service
public class V_TransitionPopulationService {
	@Autowired
	V_TransitionPopulationRepository transitionPopulationRepository;
	@Autowired
	AgentRepository agentRepository;
	@Autowired
	UniversityRankRepository universityRankRepository;

	//月別集計用
	public static final int JANUARY = 1;
	public static final int FEBRUARY = 2;
	public static final int MARCH = 3;
	public static final int APRIL = 4;
	public static final int MAY = 5;
	public static final int JUNE = 6;
	public static final int JULY = 7;
	public static final int AUGUST = 8;
	public static final int SEPTEMBER = 9;
	public static final int OCTOBER = 10;
	public static final int NOVEMBER = 11;
	public static final int DECEMBER = 12;

	/**
	 * 移行率分析
	 * @param year
	 * @return
	 */
	public List<List<TransitionRateBean>> findByStatisticsTransitionrate(Integer year) {
		List<List<V_TransitionPopulation>> transitionPopulation = sortByMonth(
				transitionPopulationRepository.findByInsertYearAndInsertMonth(year));
		return countExecute(transitionPopulation);
	}

	/**
	 * エージェント別の移行率分析
	 * @param year
	 * @param agentId
	 * @return
	 */
	public List<List<TransitionRateBean>> findByStatisticsTransitionrateAgent(Integer year, Integer agentId) {
		if (agentId == null) {
			agentId = (agentRepository.findFirstByDeleteFlag(DeleteFlagConstant.NOT_DELETED)).getAgentId();
		}
		List<List<V_TransitionPopulation>> transitionPopulation = sortByMonth(
				transitionPopulationRepository.findByInsertYearAndAgent(year, agentId));
		return countExecute(transitionPopulation);
	}

	/**
	 * 大学ランク別の移行率分析
	 * @param year
	 * @param universityRankId
	 * @return
	 */
	public List<List<TransitionRateBean>> findByStatisticsTransitionrateUniversityRank(Integer year,
			Integer universityRankId) {
		if (universityRankId == null) {
			universityRankId = universityRankRepository
					.findFirstByDeleteFlagOrderByUniversityRankId(DeleteFlagConstant.NOT_DELETED).getUniversityRankId();
		}
		List<List<V_TransitionPopulation>> transitionPopulation = sortByMonth(
				transitionPopulationRepository.findByInsertYearAndUniversityRank(year, universityRankId));
		return countExecute(transitionPopulation);
	}

	/**
	 * 月別の集計を実行
	 * @param transitionPopulation
	 * @return
	 */
	public List<List<TransitionRateBean>> countExecute(List<List<V_TransitionPopulation>> transitionPopulation) {
		List<List<TransitionRateBean>> transitionRateResult = new ArrayList<>();
		//月別の集計結果を画面表示用Beanクラスに格納
		for (List<V_TransitionPopulation> v : transitionPopulation) {
			transitionRateResult.add(countByStatus(v));
		}
		return transitionRateResult;
	}

	/**
	 * 月別の選考ステータスを集計し、結果を返す
	 * @param transitionPopulationByMonth
	 * @return
	 */
	public List<TransitionRateBean> countByStatus(List<V_TransitionPopulation> transitionPopulationByMonth) {
		//内定確定者数
		Double informalOffers = ((Integer) countInformalOffer(transitionPopulationByMonth)).doubleValue();
		//内定辞退OR応諾待ち数
		Double informalOfferRefusals = ((Integer) countInformalOfferFailure(transitionPopulationByMonth)).doubleValue();
		//最終面接脱落者数
		Double lastInterviewFailures = ((Integer) countLastInterviewFailure(transitionPopulationByMonth)).doubleValue();
		//二次面接脱落者数
		Double secondInterviewFailures = ((Integer) countSecondInterviewFailure(transitionPopulationByMonth))
				.doubleValue();
		//一次面接脱落者数
		Double firstInterviewFailures = ((Integer) countFirstInterviewRefusal(transitionPopulationByMonth))
				.doubleValue();
		//書類選考不合格者数
		Double applicantScreeningFailure = ((Integer) countApplicantScreeningFailure(transitionPopulationByMonth))
				.doubleValue();
		//説明会不合格者数
		Double informationSessionFailures = ((Integer) countInformationSessionFailure(transitionPopulationByMonth))
				.doubleValue();

		//全件数
		Double hitAll = ((Integer) transitionPopulationByMonth.size()).doubleValue();
		//それぞれの該当者数
		//内定者数
		Double informalOfferCounts = informalOffers + informalOfferRefusals;
		//二次面接合格者数
		Double secondInterviewCounts = informalOfferCounts + lastInterviewFailures
				+ ((Integer) countSecondInterviewPassing(transitionPopulationByMonth)).doubleValue();
		//一次面接合格者数
		Double firstInterviewPassingCounts = secondInterviewCounts + secondInterviewFailures
				+ ((Integer) countFirstInterviewPassing(transitionPopulationByMonth)).doubleValue();
		//一次面接希望者数
		Double firstInterviewCounts = firstInterviewPassingCounts
				+ ((Integer) countFirstInterviewFailure(transitionPopulationByMonth)).doubleValue()
				+ ((Integer) countFirstInterviewSelecting(transitionPopulationByMonth)).doubleValue();
		//説明会・書類選考合格
		Double apScePassingCounts = hitAll - (applicantScreeningFailure + informationSessionFailures);//hitAll - (applicantScreeningFailure + informationSessionFailures)
		//内定応諾者数
		TransitionRateBean informalOfferAcceptance = new TransitionRateBean();
		informalOfferAcceptance.setHitNum(informalOffers.intValue());
		informalOfferAcceptance.setPopulationRate(informalOffers / hitAll * 100);
		informalOfferAcceptance.setTransitionRate(informalOffers / informalOfferCounts * 100);
		informalOfferAcceptance.setRowName("内定応諾");
		//内定者数
		TransitionRateBean informalOfferResult = new TransitionRateBean();
		informalOfferResult.setHitNum(informalOfferCounts.intValue());
		informalOfferResult.setPopulationRate(informalOfferCounts / hitAll * 100);
		informalOfferResult.setTransitionRate(informalOfferCounts / secondInterviewCounts * 100);
		informalOfferResult.setRowName("内定");
		//二次面接合格者数
		TransitionRateBean secondInterviewPassing = new TransitionRateBean();
		secondInterviewPassing.setHitNum(secondInterviewCounts.intValue());
		secondInterviewPassing.setPopulationRate(secondInterviewCounts / hitAll * 100);
		secondInterviewPassing.setTransitionRate(secondInterviewCounts / firstInterviewPassingCounts * 100);
		secondInterviewPassing.setRowName("二次面接合格");
		//一次面接合格者数
		TransitionRateBean firstInterviewPassing = new TransitionRateBean();
		firstInterviewPassing.setHitNum(firstInterviewPassingCounts.intValue());
		firstInterviewPassing.setPopulationRate(firstInterviewPassingCounts / hitAll * 100);
		firstInterviewPassing.setTransitionRate(firstInterviewPassingCounts / firstInterviewCounts * 100);
		firstInterviewPassing.setRowName("一次面接合格");
		//一次面接希望者数
		TransitionRateBean firstInterviewApplicants = new TransitionRateBean();
		firstInterviewApplicants.setHitNum(firstInterviewCounts.intValue());
		firstInterviewApplicants.setPopulationRate(firstInterviewCounts / hitAll * 100);
		firstInterviewApplicants.setTransitionRate(firstInterviewCounts / apScePassingCounts * 100);
		firstInterviewApplicants.setRowName("一次面接希望");
		//説明会・書類選考合格
		TransitionRateBean apScePassing = new TransitionRateBean();
		apScePassing.setHitNum(apScePassingCounts.intValue());
		apScePassing.setPopulationRate(apScePassingCounts / hitAll * 100);
		apScePassing.setTransitionRate(apScePassingCounts / hitAll * 100);
		apScePassing.setRowName("書類選考合格");
		//月別該当者総数(書類選考受付人数)
		TransitionRateBean hitNumAll = new TransitionRateBean();
		hitNumAll.setHitNum(transitionPopulationByMonth.size());
		hitNumAll.setPopulationRate(hitNumAll.getHitNum().doubleValue() / hitNumAll.getHitNum().doubleValue() * 100);
		hitNumAll.setRowName("応募者数");
		//hitNumAll.setTransitionRate(null);

		//該当月の集計データ格納
		List<TransitionRateBean> transitionRatesByMonth = new ArrayList<>();
		transitionRatesByMonth.add(hitNumAll);
		transitionRatesByMonth.add(apScePassing);
		transitionRatesByMonth.add(firstInterviewApplicants);
		transitionRatesByMonth.add(firstInterviewPassing);
		transitionRatesByMonth.add(secondInterviewPassing);
		transitionRatesByMonth.add(informalOfferResult);
		transitionRatesByMonth.add(informalOfferAcceptance);

		return transitionRatesByMonth;
	}

	/**
	 * 内定確定者数計算(内定応諾待ち含まず)
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformalOffer(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：内定 詳細：承諾
			//選考ステータス：入社手続き 詳細：確定
			//選考ステータス：入社手続き 詳細：完了の候補者を集計
			if ((v.getSelectionStatusId() == SlcStatusConstant.INFORMAL_OFFER
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.ACCEPTANCE)
					|| (v.getSelectionStatusId() == SlcStatusConstant.JOINNING_PROCEDURE
							&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.CONFIRMED
									|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FINISHED))) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 内定辞退者OR内定応諾者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformalOfferFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：最終面接 詳細：合格
			//選考ステータス：内定 詳細：応諾待ち
			//選考ステータス：内定 詳細：辞退の候補者を集計
			if ((v.getSelectionStatusId() == SlcStatusConstant.INFORMAL_OFFER
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.WATING_ACCEPTANCE))
					|| (v.getSelectionStatusId() == SlcStatusConstant.LAST_INTERVIEW
							&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.PASSING)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 内定辞退者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformalOfferRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：内定 詳細：辞退の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.INFORMAL_OFFER
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 最終面接辞退OR不合格者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countLastInterviewFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：最終面接 詳細：辞退
			//選考ステータス：最終面接 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.LAST_INTERVIEW
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 最終面接辞退者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countLastInterviewRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：最終面接 詳細：辞退の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.LAST_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 二次選考合格者数(最終選考脱落者を除く)
	 * @param transitionPopulation
	 * @return
	 */
	public static int countSecondInterviewPassing(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：最終面接 詳細：選考中
			//選考ステータス：二次面接 詳細：合格の候補者を集計
			if ((v.getSelectionStatusId() == SlcStatusConstant.LAST_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.SELECTING)
					|| (v.getSelectionStatusId() == SlcStatusConstant.SECOND_INTERVIEW
							&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.PASSING)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 二次面接辞退OR不合格者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countSecondInterviewFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：二次面接 詳細：辞退
			//選考ステータス：二次面接 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.SECOND_INTERVIEW
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 二次面接辞退者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countSecondInterviewRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：二次面接 詳細：辞退の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.SECOND_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 一次面接合格者数(二次面接脱落者を除く)
	 * @param transitionPopulation
	 * @return
	 */
	public static int countFirstInterviewPassing(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：一次面接 詳細：合格
			//選考ステータス：二次面接 詳細：選考中の候補者を集計
			if ((v.getSelectionStatusId() == SlcStatusConstant.FIRST_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.PASSING)
					|| (v.getSelectionStatusId() == SlcStatusConstant.SECOND_INTERVIEW
							&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.SELECTING)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 一次面接辞退OR不合格者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countFirstInterviewRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：一次面接 詳細：辞退
			//選考ステータス：一次面接 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.FIRST_INTERVIEW
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.REFUSAL)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 一次面不合格者数
	 * @param transitionPopulation
	 * @return
	 */
	public static int countFirstInterviewFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：一次面接 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.FIRST_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 一次面接選考中
	 * @param transitionPopulation
	 * @return
	 */
	public static int countFirstInterviewSelecting(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：一次面接 詳細：選考中の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.FIRST_INTERVIEW
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.SELECTING) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 書類選考不合格者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countApplicantScreeningFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：書類選考 詳細：選考中
			//選考ステータス：書類選考 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.APPLICANT_SCREENING
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.SELECTING)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 書類選考不合格者数のみを計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countApplicantScreeningRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：書類選考 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.APPLICANT_SCREENING
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 書類選考合格者数のみを計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countApplicantScreeningPassing(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：書類選考 詳細：合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.APPLICANT_SCREENING
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.PASSING) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 説明会不合格者OR選考中数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformationSessionFailure(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：説明会 詳細：選考中
			//選考ステータス：説明会 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.INFORMATION_SESSION
					&& (v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE
							|| v.getSelectionStatusDetailId() == SlcStatusDtlConstant.SELECTING)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 説明会不合格者数のみを計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformationSessionRefusal(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：説明会 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.INFORMATION_SESSION
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.FAILURE) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 説明会合格者数を計算
	 * @param transitionPopulation
	 * @return
	 */
	public static int countInformationSessionPassing(List<V_TransitionPopulation> transitionPopulation) {
		int count = 0;
		for (V_TransitionPopulation v : transitionPopulation) {
			//選考ステータス：説明会 詳細：不合格の候補者を集計
			if (v.getSelectionStatusId() == SlcStatusConstant.INFORMATION_SESSION
					&& v.getSelectionStatusDetailId() == SlcStatusDtlConstant.PASSING) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 月別にデータを分類する
	 * @param transitionPopulation
	 * @return
	 */
	public List<List<V_TransitionPopulation>> sortByMonth(List<V_TransitionPopulation> transitionPopulation) {
		List<List<V_TransitionPopulation>> classifiedTspl = new ArrayList<>();
		List<V_TransitionPopulation> january = new ArrayList<>();
		List<V_TransitionPopulation> february = new ArrayList<>();
		List<V_TransitionPopulation> march = new ArrayList<>();
		List<V_TransitionPopulation> april = new ArrayList<>();
		List<V_TransitionPopulation> may = new ArrayList<>();
		List<V_TransitionPopulation> june = new ArrayList<>();
		List<V_TransitionPopulation> july = new ArrayList<>();
		List<V_TransitionPopulation> august = new ArrayList<>();
		List<V_TransitionPopulation> september = new ArrayList<>();
		List<V_TransitionPopulation> october = new ArrayList<>();
		List<V_TransitionPopulation> november = new ArrayList<>();
		List<V_TransitionPopulation> december = new ArrayList<>();

		for (V_TransitionPopulation v : transitionPopulation) {
			switch (v.getInsertMonth()) {
			case JANUARY:
				january.add(v);
				break;
			case FEBRUARY:
				february.add(v);
				break;
			case MARCH:
				march.add(v);
				break;
			case APRIL:
				april.add(v);
				break;
			case MAY:
				may.add(v);
				break;
			case JUNE:
				june.add(v);
				break;
			case JULY:
				july.add(v);
				break;
			case AUGUST:
				august.add(v);
				break;
			case SEPTEMBER:
				september.add(v);
				break;
			case OCTOBER:
				october.add(v);
				break;
			case NOVEMBER:
				november.add(v);
				break;
			case DECEMBER:
				december.add(v);
				break;
			}
		}
		classifiedTspl.add(april);
		classifiedTspl.add(may);
		classifiedTspl.add(june);
		classifiedTspl.add(july);
		classifiedTspl.add(august);
		classifiedTspl.add(september);
		classifiedTspl.add(october);
		classifiedTspl.add(november);
		classifiedTspl.add(december);
		classifiedTspl.add(january);
		classifiedTspl.add(february);
		classifiedTspl.add(march);
		classifiedTspl.add(transitionPopulation);
		//System.out.println("一月分データ" + classifiedTspl.get(JANUARY).size());
		//System.out.println("総計：" + classifiedTspl.get(12).size());
		return classifiedTspl;
	}

}
