package jp.co.tc.recruit.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.entity.selection.SelectionStatus;
import jp.co.tc.recruit.repository.SelectionStatusRepository;

/**
 * 選考ステータスのサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class SelectionStatusService {

	@Autowired
	SelectionStatusRepository slcStatusRepo;

	private final int UNNECESSARY_STATUS = 0;

	public List<SelectionStatus> findAll() {
		List<SelectionStatus> selectionStatusList =  slcStatusRepo.findByDeleteFlagOrderBySelectionProcedure(DeleteFlagConstant.NOT_DELETED);
		for(int i = 0; i < selectionStatusList.size() ;i++) {
			if(selectionStatusList.get(i).getSlcStatusId() == UNNECESSARY_STATUS) {
				selectionStatusList.remove(i);
			}
		}
		return selectionStatusList;
	}

	public SelectionStatus promoteSlcStatus(Integer selectionStatusId) {
		List<SelectionStatus> selectionStatusList = slcStatusRepo.findByDeleteFlagOrderBySelectionProcedure(DeleteFlagConstant.NOT_DELETED);
		for(int i = 0; i < selectionStatusList.size() ;i++) {
			if(selectionStatusList.get(i).getSlcStatusId() == selectionStatusId) {
				return selectionStatusList.get(i+1);
			}
		}
		return slcStatusRepo.getOne(UNNECESSARY_STATUS);
	}


}