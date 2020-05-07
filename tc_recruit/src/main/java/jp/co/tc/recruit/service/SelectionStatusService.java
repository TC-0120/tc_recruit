package jp.co.tc.recruit.service;

<<<<<<< HEAD
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
=======
import java.util.List;
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import jp.co.tc.recruit.repository.SelectionRepository;
=======
import jp.co.tc.recruit.entity.SelectionStatus;
>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
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
<<<<<<< HEAD
	@Autowired
	SelectionRepository repository;

	/*メソッド名一緒なのでエラーになってます。引数入れますか？？
	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findAll(Sort.by("slcStatusId"));
	}

	public SelectionRepository findById(Integer slcPK) {
		return repository.findBySlcPK(slcPK);
	}

	public SelectionRepository save(SelectionRepository selection) {
		return repository.save(selection);
	}
	*/
	public String getStringDate(Date date) {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").format(date);
	}

	public Date setDate(String stringDate) throws ParseException {
		return new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(stringDate);
	}
=======

	public List<SelectionStatus> findAll() {
		return slcStatusRepo.findByOrderBySlcStatusId();
	}

>>>>>>> 7827f19e166a8ddb2c63b84dc03b27e6806564f4
}
