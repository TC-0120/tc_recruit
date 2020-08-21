package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.constant.SlcStatusConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.form.MultipleUpdateForm;
import jp.co.tc.recruit.repository.AgentRepository;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;
import jp.co.tc.recruit.repository.SelectionStatusDetailRepository;
import jp.co.tc.recruit.repository.SelectionStatusRepository;
import jp.co.tc.recruit.repository.educational.EducationalBackgroundRepository;

/**
 * 候補者情報のサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class CandidateService {

	@Autowired
	CandidateRepository candidateRepo;
	@Autowired
	SelectionRepository slcRepo;
	@Autowired
	SelectionStatusRepository slcStatusRepo;
	@Autowired
	SelectionStatusDetailRepository slcStatusDtlRepo;
	@Autowired
	AgentRepository agentRepo;
	@Autowired
	AbstractEntityService abstractEntityService;
	@Autowired
	EducationalBackgroundRepository educationalBackgroundRepository;
	@Autowired
	SelectionStatusService selectionStatusService;
	@Autowired
	SelectionService slcService;

	//学歴情報をマスタデータから参照しない場合
	private static final int NO_REFERRER_DATA = 0;

	public Candidate findById(Integer id) {
		return candidateRepo.findByCandidateIdAndDeleteFlag(id, DeleteFlagConstant.NOT_DELETED);
	}

	public List<Candidate> findAll() {
		return candidateRepo.findAll();
	}

	/**
	 * 候補者情報の登録
	 *
	 * @param candidate 候補者情報
	 * @param slcDate 選考日程
	 */
	public void register(Candidate candidate, String slcDate) {
		Integer slcStatusDtlId = null;

		//		if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.INFORMATION_SESSION) {
		//			//選考ステータスが説明会の場合、選考ステータス詳細を空白に
		//			//slcStatusDtlId = SlcStatusDtlConstant.BLANK;
		//		} else if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.APPLICANT_SCREENING) {
		//			//選考ステータスが書類選考の場合
		//			if (slcDate.isEmpty() || slcDate == null) {
		//				//選考日程が空白の場合、選考ステータス詳細を調整中に
		//				slcStatusDtlId = SlcStatusDtlConstant.ADJUSTING;
		//			} else {
		//				//選考日程が空白でない場合、選考ステータス詳細を選考中に
		//				slcStatusDtlId = SlcStatusDtlConstant.SELECTING;
		//			}
		//		}
		//書類選考・説明会共に選考ステータス詳細は選考中へ
		slcStatusDtlId = SlcStatusDtlConstant.SELECTING;
		//選考ステータス詳細を設定
		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcStatusDtlId));

		if (candidate.getEducationalBackground() == null) {
			candidate.setEducationalBackground(educationalBackgroundRepository.getOne(NO_REFERRER_DATA));
		} else {
			abstractEntityService.setInsertUer(candidate.getEducationalBackground());
		}
		//登録者IDを設定
		abstractEntityService.setInsertUer(candidate);

		candidateRepo.save(candidate);
	}

	/**
	 * 候補者情報の更新
	 *
	 * @param candidate 候補者情報
	 */
	public void update(Candidate candidate) {
		//		//選考ステータスが説明会でない、かつ、選考ステータス詳細が空白である場合
		//		if ((candidate.getSlcStatus().getSlcStatusId() != SlcStatusConstant.INFORMATION_SESSION
		//				&& candidate.getSlcStatusDtl().getSlcStatusDtlId() == SlcStatusDtlConstant.BLANK)) {
		//			//選考ステータス詳細を調整中に
		//			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
		//		}
		if (candidate.getEducationalBackground() == null) {
			candidate.setEducationalBackground(educationalBackgroundRepository.getOne(NO_REFERRER_DATA));
		} else {
			if (candidate.getEducationalBackground().getEducationalBackgroundId() == candidateRepo
					.getOne(candidate.getCandidateId()).getEducationalBackground().getEducationalBackgroundId()) {
				abstractEntityService.setUpdateUser(candidate.getEducationalBackground(), candidate.getCandidateId());
			} else {
				abstractEntityService.setInsertUer(candidate.getEducationalBackground());
			}
		}
		//更新者IDを設定
		abstractEntityService.setUpdateUser(candidate, candidate.getCandidateId());
		candidateRepo.save(candidate);
	}

	public void delete(Integer candidateId) {
		Candidate candidate = candidateRepo.getOne(candidateId);
		//削除フラグ更新
		candidate.setDeleteFlag(DeleteFlagConstant.DELETED);
		abstractEntityService.setUpdateUser(candidate, candidateId);
		candidateRepo.save(candidate);
	}

	/**
	 * 選考ステータス詳細の更新
	 *
	 * @param cId 候補者ID
	 * @param slcResult 選考結果
	 * @param slcDate 選考日程
	 */
	public void updateSlcStatusDtl(Integer cId, Integer slcResult, String slcDate) {
		Candidate candidate = findById(cId);

		//		if (slcResult == SlcStatusDtlConstant.PENDING) {
		//			if (slcDate.isEmpty() || slcDate == null) {
		//				//選考結果が保留中、かつ、選考日程が空白の場合、選考ステータス詳細を調整中に
		//				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.ADJUSTING));
		//			} else {
		//				//選考結果が保留中、かつ、選考日程が空白でない場合、選考ステータス詳細を選考中に
		//				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.SELECTING));
		//			}
		//		} else {
		//			//選考結果が保留中でない場合、選考結果の入力値を選考ステータス詳細に保存
		//			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcResult));
		//		}
		candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(slcResult));

		if (candidate.getSlcStatus().getSlcStatusId() == SlcStatusConstant.JOINNING_PROCEDURE) {
			if (slcDate.isEmpty() || slcDate == null) {
				//選考ステータスが入社手続き、かつ、選考日程が空白の場合、選考ステータス詳細を調整中に
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.CONFIRMED));
			} else {
				//選考ステータスが入社手続き、かつ、選考日程が空白の場合、選考ステータス詳細を確定に
				//candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.FINISHED));
			}
		}

		candidateRepo.save(candidate);
	}

	/**
	 * 選考ステータスの繰り上げ
	 *
	 * @param cId 候補者ID
	 * @return 選考ステータスが最後（入社手続き）でないかどうか
	 */
	public void promoteSlcStatus(Integer cId) {
		Candidate candidate = findById(cId);
		Integer slcStatusId = candidate.getSlcStatus().getSlcStatusId();
		Integer slcStatusDtlId = candidate.getSlcStatusDtl().getSlcStatusDtlId();

		if (slcStatusDtlId == SlcStatusDtlConstant.PASSING
				|| slcStatusDtlId == SlcStatusDtlConstant.ACCEPTANCE) {
			//選考ステータスが説明会の場合は、一次面接へ繰り上げ
			if (slcStatusId == SlcStatusConstant.INFORMATION_SESSION) {
				candidate.setSlcStatus(slcStatusRepo.findBySlcStatusIdAndDeleteFlag(SlcStatusConstant.FIRST_INTERVIEW,
						DeleteFlagConstant.NOT_DELETED));
			} else {
				//選考ステータス詳細が合格、または、承諾の場合、ステータスを繰り上げ
				candidate.setSlcStatus(selectionStatusService.promoteSlcStatus(slcStatusId));
			}

			if (slcStatusId < SlcStatusConstant.LAST_INTERVIEW) {
				//選考ステータスが最終面接より前の場合、選考ステータス詳細を選考中に
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.SELECTING));
			} else if (slcStatusId == SlcStatusConstant.LAST_INTERVIEW) {
				//選考ステータスが最終面接の場合、選考ステータス詳細を応諾待ちに
				candidate
						.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.WATING_ACCEPTANCE));
			} else if (slcStatusId == SlcStatusConstant.INFORMAL_OFFER) {
				//選考ステータスが内定の場合、選考ステータス詳細を確定に
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.CONFIRMED));
			}
		}
		//選考ステータス詳細が確定の場合（選考ステータスが入社手続きの場合のみ）
		if (slcStatusDtlId == SlcStatusDtlConstant.CONFIRMED) {
			//選考ステータス詳細を完了に
			candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(SlcStatusDtlConstant.FINISHED));
		}
		slcService.update(cId, candidate.getSlcStatus().getSlcStatusId(), null);
		candidateRepo.save(candidate);
	}

	/**
	 * 候補者情報の一括更新
	 *
	 * @param cId 候補者ID
	 * @param muForm 一括更新フォーム
	 * @return 候補者情報
	 */
	public List<Candidate> updateList(Integer[] cId, MultipleUpdateForm muForm) {
		List<Candidate> cList = new ArrayList<Candidate>();

		for (int i = 0; i < cId.length; i++) {
			Candidate candidate = findById(cId[i]);

			//入力値がある場合、それぞれ保存
			if (muForm.getGender() != null) {
				candidate.setGender(muForm.getGender());
			}

			if (!muForm.getEduBack().isEmpty()) {
				candidate.setEduBack(muForm.getEduBack());
			}

			if (muForm.getSlcStatusId() != 0) {
				candidate.setSlcStatus(slcStatusRepo.findBySlcStatusId(muForm.getSlcStatusId()));
			}

			if (muForm.getSlcStatusDtlId() != 0) {
				candidate.setSlcStatusDtl(slcStatusDtlRepo.findBySlcStatusDtlId(muForm.getSlcStatusDtlId()));
			}

			if (muForm.getAgentId() != 0) {
				candidate.setAgent(agentRepo.findByAgentId(muForm.getAgentId()));
			}

			//			if (muForm.getReferrerId() != 0) {
			//				candidate.setReferrer(referrerRepo.findByReferrerId(muForm.getReferrerId()));
			//			}

			cList.add(candidate);
		}

		candidateRepo.saveAll(cList);
		return cList;
	}

	public void deleteList(Integer[] cId) {
		for (int i = 0; i < cId.length; i++) {
			candidateRepo.deleteById(cId[i]);
		}
	}

}
