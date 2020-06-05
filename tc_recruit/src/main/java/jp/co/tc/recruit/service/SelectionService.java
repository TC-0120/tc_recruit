package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.entity.Selection.SelectionPK;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;
import jp.co.tc.recruit.util.DateFormatter;

/**
 * 選考情報のサービスクラス
 *
 * @author TC-0115
 *
 */
@Service
public class SelectionService {

	@Autowired
	SelectionRepository slcRepo;
	@Autowired
	CandidateRepository candidateRepo;

	public List<Selection> findAll() {
		return slcRepo.findAll();
	}

	/**
	 * 選考情報の検索
	 *
	 * @param slcPK 選考情報の複合主キー
	 * @return 選考情報
	 */
	public Selection findById(SelectionPK slcPK) {
		//複合主キーから選考情報を取得
		Selection slc = slcRepo.findBySlcPK(slcPK);
		//選考情報がなかった場合、その複合主キーで新規登録し、それを返す（選考情報がないとエラーがでるため）
		if (slc == null) {
			Selection slcNew = new Selection(slcPK);
			slcRepo.save(slcNew);
			return slcNew;
		}
		return slc;
	}

	public void update(Selection slc, String slcDateString) {
		slc.setSlcDate(DateFormatter.toDate(slcDateString));
		slcRepo.save(slc);
	}

	/**
	 * 選考情報の登録
	 *
	 * @param cId 候補者ID
	 * @param sId 選考ステータスID
	 * @param slcDate 選考日程
	 */
	public void register(Integer cId, Integer sId, String slcDate) {
		Selection slc = findById(new SelectionPK(cId, sId));
		//選考日程をString型からDate型に変換、保存
		slc.setSlcDate(DateFormatter.toDate(slcDate));
		slcRepo.save(slc);
	}

	public void deleteByCandidateId(Integer id) {
		slcRepo.deleteByCandidateId(id);
	}

	/**
	 * 選考日程の一括更新
	 *
	 * @param cList 候補者情報
	 * @param slcDateString 選考日程
	 */
	public void updateList(List<Candidate> cList, String slcDateString) {
		//入力値がない場合、処理不要
		if (slcDateString == null) {
			return;
		}
		//選考日程をString型からDate型に変換
		Date slcDate = DateFormatter.toDate(slcDateString);
		List <Selection> slcList = new ArrayList<Selection>();

		for (int i = 0; i < cList.size(); i++) {
			SelectionPK slcPK = new SelectionPK(cList.get(i).getCandidateId(), cList.get(i).getSlcStatus().getSlcStatusId());
			Selection slc = findById(slcPK);
			slc.setSlcDate(slcDate);
			slcList.add(slc);
		}

		slcRepo.saveAll(slcList);
	}

	public void deleteList(Integer[] cId) {
		for (int i = 0; i < cId.length; i++) {
			slcRepo.deleteByCandidateId(cId[i]);
		}
	}
}
