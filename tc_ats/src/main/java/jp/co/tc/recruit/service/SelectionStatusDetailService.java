package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.selection.SelectionStatusDetail;
import jp.co.tc.recruit.repository.SelectionStatusDetailRepository;

/**
 * 選考ステータス詳細のサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class SelectionStatusDetailService {

	@Autowired
	SelectionStatusDetailRepository slcStatusDtlRepo;

	private final int UNNECESSARY_STATUS = 0;

	public List<SelectionStatusDetail> findAll() {
		List<SelectionStatusDetail> selectionStatusDtlList = slcStatusDtlRepo.findAllByOrderBySlcStatusDtlId();
		for(int i = 0; i < selectionStatusDtlList.size() ;i++) {
			if(selectionStatusDtlList.get(i).getSlcStatusDtlId() == UNNECESSARY_STATUS) {
				selectionStatusDtlList.remove(i);
			}
		}
		return selectionStatusDtlList;
	}

}
