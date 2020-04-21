package jp.co.tc.recruit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Selection save(Selection slc) {
		return slcRepo.save(slc);
	}

	public void deleteByCandidateId(Integer id) {
		slcRepo.deleteByCandidateId(id);
	}

	public String getStringDate(Date date) {
		if (date == null) {
			return null;
		} else {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);
		}
	}

	public Date setDate(String stringDate) throws ParseException {
		if (stringDate.isEmpty() || stringDate == null) {
			return null;
		} else {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(stringDate);
		}
	}

}
