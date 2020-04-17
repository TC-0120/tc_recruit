package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.repository.SelectionStatusRepository;

@Service
public class SelectionStatusService {

	@Autowired
	SelectionStatusRepository slcStatusRepo;

	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findAll();
	}


	/*メソッド名一緒なのでエラーになってます。引数入れますか？？*/
	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findAll(Sort.by("slcStatusId"));
	}

	/*public SelectionRepository findById(Integer slcPK) {
		return repository.findBySlcPK(slcPK);
	}

	public SelectionRepository save(SelectionRepository selection) {
		return repository.save(selection);
	}

	public String getStringDate(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").format(date);
	}

	public Date setDate(String stringDate) throws ParseException {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(stringDate);
	}*/
}
