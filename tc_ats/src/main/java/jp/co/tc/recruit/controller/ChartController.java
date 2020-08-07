package jp.co.tc.recruit.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.repository.MessageStatusRepository;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.service.UserService;

/**
 * 画面設定機能のコントローラー
 *
 * @author TC-0117
 */
@Controller
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	SelectionStatusService slcSttSvc;
	@Autowired
	SelectionStatusDetailService slcSttDtlSvc;
	@Autowired
	MessageStatusService msgSttSvc;
	@Autowired
	MessageStatusRepository msgSttRepo;
	@Autowired
	UserService usrSvc;

	/**
	 * メンテナンス可能項目の表示
	 *
	 * @param model
	 * @return 画面設定メニュー
	 */
	@GetMapping()
	public String getMaintenance(Model model) {
		// 横軸
		Date date = new Date();
		List<String> labelList = new ArrayList<String>();
		String thisMonth = null;
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

		//過去半年の月を取得(yyyy-MM)
		for (int i = -5; i <= 0; i++) {
			cal.setTime(date);
			cal.add(Calendar.MONTH, i);
			thisMonth = dateFormat.format(cal.getTime());
			labelList.add(thisMonth);
		}

		//合格者数
		int point[] = { 3, 2, 1, 2, 1, 2, 2};

		model.addAttribute("label", labelList);
		model.addAttribute("point", point);

		return "chart";
	}
}