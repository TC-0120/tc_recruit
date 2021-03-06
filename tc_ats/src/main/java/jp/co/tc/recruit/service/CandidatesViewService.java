package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.constant.SlcStatusDtlConstant;
import jp.co.tc.recruit.entity.Dashboard;
import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.entity.view.V_Candidates;
import jp.co.tc.recruit.form.ConditionsForm;
import jp.co.tc.recruit.repository.CandidatesViewRepository;
import jp.co.tc.recruit.repository.UserRepository;

@Service
public class CandidatesViewService {

	@Autowired
	CandidatesViewRepository candidatesViewRepository;
	@Autowired
	UserRepository userRepository;

	public List<V_Candidates> findAll(){
		return candidatesViewRepository.findByDeleteFlagOrderByCandidateId(DeleteFlagConstant.NOT_DELETED);
	}

	public List<Integer> showDashboard(List<Dashboard> dashboard){
		List<Integer> countList = new ArrayList<>();
		for(Dashboard v : dashboard) {
			countList.add(countAllList(v.getSlcStatus().getSlcStatusId(), v.getSlcStatusDtl().getSlcStatusDtlId()));
		}
		return countList;
	}

	public int countAllList(Integer slcStatusId, Integer slcstatusDtlId){
		if( slcStatusId == 0 && slcstatusDtlId == 0) {
			return countAllStatus();
		}else if(slcStatusId != 0 && slcstatusDtlId == 0){
			return candidatesViewRepository.findBySlcStatusIdAndDeleteFlag(slcStatusId, DeleteFlagConstant.NOT_DELETED).size();
		}else if( slcStatusId == 0 && slcstatusDtlId != 0) {
			return candidatesViewRepository.findBySlcStatusDtlIdAndDeleteFlag(slcstatusDtlId, DeleteFlagConstant.NOT_DELETED).size();
		}else {
			return candidatesViewRepository.findBySlcStatusIdAndSlcStatusDtlIdAndDeleteFlag(slcStatusId, slcstatusDtlId, DeleteFlagConstant.NOT_DELETED).size();
		}
	}

	public int countAllStatus() {
		List<V_Candidates> allList = findAll();
		int count = 0;
		for(V_Candidates v : allList) {
			if(v.getSlcStatusDtlId() == 4 || v.getSlcStatusDtlId() == 5 || v.getSlcStatusDtlId() == 9) {
				continue;
			}else {
				count++;
			}
		}
		return count;
	}


	/**
	 * 候補者情報の検索
	 *
	 * @param cf 検索条件
	 * @return 候補者情報
	 */
	public List<V_Candidates> findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(ConditionsForm cf) {
		List<V_Candidates> cv = new ArrayList<V_Candidates>();

		//検索条件から候補者情報を取得
		cv = candidatesViewRepository.findBySlcStatusIdAndSlcStatudDtlIdAndSlcDate(cf);

		//登録・更新者情報取得
		for(V_Candidates v:cv) {
			if(v.getInsertUser() == null ) {
				User user = new User();
				user.setLastName("DB登録");
				v.setInsertUserData(user);
			}else {
				v.setInsertUserData(userRepository.getOne(v.getInsertUser()));
			}
			if(v.getUpdateUser() == null) {
				User user = new User();
				user.setLastName("DB登録");
				v.setUpdateUserData(user);
			}else {
				v.setUpdateUserData(userRepository.getOne(v.getUpdateUser()));
			}
		}

		//		//ソート項目が選考日程の場合、Comparatorクラスを用いて並び替え
		//		if (cf.getOrder() == ConditionsForm.SLC_DATE) {
		//			Collections.sort(cv, new SlcDateComparator());
		//
		//			if (cf.getDirection() == ConditionsForm.DESC) {
		//				Collections.reverse(cv);
		//			}
		//		}


		return cv;
	}

	/**
	 * 選考日程のComparatorクラス
	 *
	 * @author TC-0115
	 *
	 */
	private class SlcDateComparator implements Comparator<V_Candidates> {
		public int compare(V_Candidates cv1, V_Candidates cv2) {
			Integer cvStDtl1 = cv1.getSlcStatusDtlId();
			Integer cvStDtl2 = cv2.getSlcStatusDtlId();

			//並び替えのルール
			//一覧画面で選考日程を表示する選考ステータス詳細を先に並べる
			//一覧画面で選考日程を表示する選考ステータス詳細（選考中、承諾待ち、確定）同士の比較の場合、選考日程が早い順に並べる
			//一覧画面で選考日程を表示しない選考ステータス詳細同士の比較の場合、選考ステータス詳細の小さい順に並べる
			if (cvStDtl1 == SlcStatusDtlConstant.SELECTING || cvStDtl1 == SlcStatusDtlConstant.WATING_ACCEPTANCE
					|| cvStDtl1 == SlcStatusDtlConstant.CONFIRMED) {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING || cvStDtl2 == SlcStatusDtlConstant.WATING_ACCEPTANCE
						|| cvStDtl2 == SlcStatusDtlConstant.CONFIRMED) {
					return cv1.getSlcDate().compareTo(cv2.getSlcDate());
					} else {
					return -1;
				}
			} else {
				if (cvStDtl2 == SlcStatusDtlConstant.SELECTING || cvStDtl2 == SlcStatusDtlConstant.WATING_ACCEPTANCE
						|| cvStDtl2 == SlcStatusDtlConstant.CONFIRMED) {
					return 1;
				} else {
					return Integer.compare(cvStDtl1, cvStDtl2);
				}
			}
		}
	}
}
