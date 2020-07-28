package jp.co.tc.recruit.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.tc.recruit.entity.Agent;
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.SelectionStage;
import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.form.CandidateForm;
import jp.co.tc.recruit.repository.SelectionStatusRepository;
import jp.co.tc.recruit.service.SelectionStatusService;

public class BeanCopy {

	@Autowired
	SelectionStatusRepository selectionStatusRepository;
	@Autowired
	SelectionStatusService selectionStatusService;

	public static List<Candidate> copyFormToEntity(CandidateForm candidateForm,
			List<SelectionStatus> selectionStatus2) {
		List<Candidate> candidates = new ArrayList<>();
		for (int i = 0; i < candidateForm.getCandidateId().size(); i++) {
			Candidate candidate = new Candidate();
			Agent agent = new Agent();
			SelectionStatus selectionStatus = new SelectionStatus();
			SelectionStage selectionStage = new SelectionStage();

			//AgentFormの値をAgentエンティティに移行
			agent.setAgentId(candidateForm.getAgent().get(i).getAgentId());
			agent.setAgentName(candidateForm.getAgent().get(i).getAgentName());
			agent.setReferrerFee(candidateForm.getAgent().get(i).getReferrerFee());

			//SelectionStatusFormの値をSelectionStatusエンティティに移行
			selectionStatus.setSlcStatusId(candidateForm.getSlcStatus().get(i).getSlcStatusId());
			for (SelectionStatus v : selectionStatus2) {
				if (v.getSlcStatusId() == candidateForm.getSlcStatus().get(i).getSlcStatusId()) {
					selectionStatus.setSlcStatusName(v.getSlcStatusName());
				}
			}
			//SelectionStageFormの値をSelectionStageエンティティに移行
			selectionStage.setSelectionStageId(candidateForm.getSelectionStage().get(i).getSelectionStageId());

			selectionStage.setDocumentSelection(
					DateFormatter.toDate(candidateForm.getSelectionStage().get(i).getDocumentSelection()));
			selectionStage
					.setDocumentTotalization(DateTotalization.checkDate(selectionStage.getDocumentSelection()));
			selectionStage
					.setDocumentSelectionResult(candidateForm.getSelectionStage().get(i).getDocumentSelectionResult());

			selectionStage.setPrimaryInterview(
					DateFormatter.toDate(candidateForm.getSelectionStage().get(i).getPrimaryInterview()));
			selectionStage.setPrimaryInterviewtTotalization(
					DateTotalization.checkDate(selectionStage.getPrimaryInterview()));
			selectionStage
					.setPrimaryInterviewResult(candidateForm.getSelectionStage().get(i).getPrimaryInterviewResult());

			selectionStage.setSecondInterview(
					DateFormatter.toDate(candidateForm.getSelectionStage().get(i).getSecondInterview()));
			selectionStage.setSecondInterviewTotalization(
					DateTotalization.checkDate(selectionStage.getSecondInterview()));
			selectionStage
					.setSecondInterviewResult(candidateForm.getSelectionStage().get(i).getSecondInterviewResult());

			selectionStage.setFinalInterview(
					DateFormatter.toDate(candidateForm.getSelectionStage().get(i).getFinalInterview()));
			selectionStage
					.setFinalInterviewTotalization(DateTotalization.checkDate(selectionStage.getFinalInterview()));
			selectionStage.setFinalInterviewResult(candidateForm.getSelectionStage().get(i).getFinalInterviewResult());

			selectionStage.setUnofficialOffer(
					DateFormatter.toDate(candidateForm.getSelectionStage().get(i).getUnofficialOffer()));
			selectionStage.setUnofficialOfferTotalization(
					DateTotalization.checkDate(selectionStage.getUnofficialOffer()));
			selectionStage
					.setUnofficialOfferResult(candidateForm.getSelectionStage().get(i).getUnofficialOfferResult());

			//CandidateFormの値をCandidateエンティティに移行
			candidate.setCandidateId(candidateForm.getCandidateId().get(i));
			candidate.setCandidateName(candidateForm.getCandidateName().get(i));
			//candidate.setCandidateFurigana(candidateForm.getCandidateFurigana().get(i));
			candidate.setGender(candidateForm.getGender().get(i));
			candidate.setEduBack(candidateForm.getEduBack().get(i));
			candidate.setSlcStatus(selectionStatus);
			candidate.setSelectionStage(selectionStage);
			candidate.setAgent(agent);
			candidate.setRemarks(candidateForm.getRemarks().get(i));
			candidate.setAptitudeFlag(candidateForm.getAptitudeFlag().get(i));
			candidate.setAptitudeScore(candidateForm.getAptitudeScore().get(i));
			candidate.setDeleteFlag(candidateForm.getDeleteFlag().get(i));
			candidate.setInsertDate(DateFormatter.toDate(candidateForm.getInsertDate().get(i)));

			//エンティティリストに候補者情報を追加
			candidates.add(candidate);
		}
		return candidates;
	}
}

//     candidate.setAgent(candidateForm.getAgent());
//		candidate.setAptitudeFlag(candidateForm.getAptitudeFlag());
//		candidate.setAptitudeScore(candidateForm.getAptitudeScore());
//		candidate.setCandidateFurigana(candidateForm.getCandidateFurigana());
//		candidate.setCandidateId(candidateForm.getCandidateId());
//		candidate.setCandidateName(candidateForm.getCandidateName());
//		candidate.setEduBack(candidateForm.getEduBack());
//		candidate.setGender(candidateForm.getGender());
//		candidate.setReferrer(candidateForm.getReferrer());
//		candidate.setRemarks(candidateForm.getRemarks());
//		candidate.setSlcStatus(candidateForm.getSlcStatus());
//		candidate.setSlcStatusDtl(candidateForm.getSlcStatusDtl());
