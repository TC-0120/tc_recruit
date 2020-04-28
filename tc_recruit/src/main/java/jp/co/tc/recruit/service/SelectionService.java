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
import jp.co.tc.recruit.entity.Selection.SelectionPK;
import jp.co.tc.recruit.repository.CandidateRepository;
import jp.co.tc.recruit.repository.SelectionRepository;

@Service
public class SelectionService {

	@Autowired
	SelectionRepository slcRepo;
	@Autowired
	CandidateRepository candidateRepo;

	public List<Selection> findAll() {
		return slcRepo.findAll();
	}

	public Selection findById(SelectionPK slcPK) {
		Selection slc = slcRepo.findBySlcPK(slcPK);
		if (slc == null) {
			Selection slcNew = new Selection(slcPK);
			save(slcNew);
			return slcNew;
		}
		return slc;
	}

	public void save(Selection slc) {
		slcRepo.save(slc);
	}

	public void regist(Integer cId, Integer sId, String slcDate) {
		Selection slc = findById(new SelectionPK(cId, sId));
		slc.setSlcDate(setDate(slcDate));
		save(slc);
	}

	public void deleteByCandidateId(Integer id) {
		slcRepo.deleteByCandidateId(id);
	}

	public String getStringDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date).replace(" ", "T");
		}
	}

	public Date setDate(String stringDate){
		if (stringDate.isEmpty() || stringDate == null) {
			return null;
		} else {
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(stringDate.replace("T", " "));
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public void multipleUpdate(List<Candidate> cList, String slcDateString) {
		if (slcDateString == null) {
			return;
		}

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
