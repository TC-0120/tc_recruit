package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.AbstractEntity;
import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.selection.Selection;
import jp.co.tc.recruit.entity.selection.Selection.SelectionPK;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;
import jp.co.tc.recruit.repository.UserRepository;

/**
 *
 * @author TC-0120
 *共通カラムに対するサービスクラス
 */
@Service
public class AbstractEntityService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	SelectionRepository slcRepo;

	//データ登録時に更新者及び登録者を記録
	public void setInsertUer(AbstractEntity inheritanceEntity) {
		//登録社ID・更新者IDを設定
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		inheritanceEntity.setInsertUser((userRepository.findByUsername(principal.getUsername())).getId());
		inheritanceEntity.setUpdateUser(inheritanceEntity.getInsertUser());
	}

	public void setUpdateUser(AbstractEntity inheritanceEntity, Integer cId) {
		Candidate candidate = candidateRepository.getOne(cId);
		//登録社ID・更新者IDを設定
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		inheritanceEntity.setUpdateUser((userRepository.findByUsername(principal.getUsername())).getId());
		inheritanceEntity.setInsertUser(candidate.getInsertUser());
		inheritanceEntity.setInsertDate(candidate.getInsertDate());
	}

	public void setUpdateUser(AbstractEntity inheritanceEntity,SelectionPK slcPK) {
		Selection slc = slcRepo.findBySlcPK(slcPK);
		//登録社ID・更新者IDを設定
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		inheritanceEntity.setUpdateUser((userRepository.findByUsername(principal.getUsername())).getId());
		if(slc.getInsertUser() == null) {
			inheritanceEntity.setInsertUser((userRepository.findByUsername(principal.getUsername())).getId());
		}else {
			inheritanceEntity.setInsertUser(slc.getInsertUser());
		}
	}
}
