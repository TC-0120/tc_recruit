package jp.co.tc.recruit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.Candidate;
import jp.co.tc.recruit.entity.Selection;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;

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

<<<<<<< HEAD
	/*public Selection findById(SelectionPK slcPK) {
=======
	/**
	 * 選考情報の検索
	 *
	 * @param slcPK 選考情報の複合主キー
	 * @return 選考情報
	 */
	public Selection findById(SelectionPK slcPK) {
		//複合主キーから選考情報を取得
>>>>>>> RecruitmentManagement
		Selection slc = slcRepo.findBySlcPK(slcPK);
		//選考情報がなかった場合、その複合主キーで新規登録し、それを返す（選考情報がないとエラーがでるため）
		if (slc == null) {
			Selection slcNew = new Selection(slcPK);
			save(slcNew);
			return slcNew;
		}
		return slc;
	}
<<<<<<< HEAD
	*/
	public Selection save(Selection slc) {
		return slcRepo.save(slc);
=======

	public void save(Selection slc) {
		slcRepo.save(slc);
	}

	/**
	 * 選考情報の登録
	 *
	 * @param cId 候補者ID
	 * @param sId 選考ステータスID
	 * @param slcDate 選考日程
	 */
	public void regist(Integer cId, Integer sId, String slcDate) {
		Selection slc = findById(new SelectionPK(cId, sId));
		//選考日程をString型からDate型に変換、保存
		slc.setSlcDate(setDate(slcDate));
		save(slc);
>>>>>>> RecruitmentManagement
	}

	public void deleteByCandidateId(Integer id) {
		slcRepo.deleteByCandidateId(id);
	}

	/**
	 * 選考日程をDate型からString型に変換
	 *
	 * @param date 選考日程
	 * @return
	 */
	public String getStringDate(Date date) {
		if (date == null) {
			return null;
		} else {
			//yyyy-MM-ddとHH:mmの間にTを挟まないと、画面で登録されている値が表示できない
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date).replace(" ", "T");
		}
	}

	/**
	 * 選考日程をString型からDate型に変換
	 *
	 * @param stringDate 選考日程
	 * @return
	 */
	public Date setDate(String stringDate){
		if (stringDate.isEmpty() || stringDate == null) {
			return null;
		} else {
			try {
				//yyyy-MM-ddとHH:mmの間にTが挟まれて、画面からの入力値が送られてくる
				return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate.replace("T", " "));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 選考日程の一括更新
	 *
	 * @param cList 候補者情報
	 * @param slcDateString 選考日程
	 */
	public void multipleUpdate(List<Candidate> cList, String slcDateString) {
		//入力値がない場合、処理不要
		if (slcDateString == null) {
			return;
		}
		//選考日程をString型からDate型に変換
		Date slcDate = setDate(slcDateString);
		List <Selection> slcList = new ArrayList<Selection>();

		for (int i = 0; i < cList.size(); i++) {
			SelectionPK slcPK = new SelectionPK(cList.get(i).getCandidateId(), cList.get(i).getSlcStatus().getSlcStatusId());
			Selection slc = findById(slcPK);
			slc.setSlcDate(slcDate);
			slcList.add(slc);
		}

		slcRepo.saveAll(slcList);
	}

	public void multipleDelete(Integer[] cId) {
		for (int i = 0; i < cId.length; i++) {
			slcRepo.deleteByCandidateId(cId[i]);
		}
	}

}
