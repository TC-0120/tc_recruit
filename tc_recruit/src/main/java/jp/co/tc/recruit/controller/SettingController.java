package jp.co.tc.recruit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.form.MessageStatusForm;
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
@RequestMapping("/setting")
public class SettingController {

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
		return "/setting";
	}

	/**
	 * ダッシュボードのマスタ情報の検索、表示
	 *
	 *　@param msgSttForm
	 * @param model
	 * @return ダッシュボード画面設定画面
	 */
	@GetMapping("message_status")
	public String getDashboardMaintenance(@ModelAttribute("MessageStatus") MessageStatusForm msgSttForm, Model model) {
		List<MessageStatus> msgSttList;
		/* ダッシュボードでの表示名、選考ステータス、選考ステータス詳細情報をソート番号昇順に取得 */
		msgSttList = msgSttSvc.findAllByOrderBySort();
		/* ダッシュボードでの表示名、選考ステータス、選考ステータス詳細情報をmodelに格納 */
		model.addAttribute("msgSttList", msgSttList);
		return "setting/message_status";
	}

	/**
	 * ステータスメッセージの一括更新
	 *
	 * @param msgSttForm
	 * @param model
	 * @return ダッシュボード
	 */
	@PostMapping("message_status/update")
	@Transactional(readOnly = false)
	public String updateMessageStatus(@ModelAttribute("MessageStatus") MessageStatusForm msgSttForm, Model model) {
		// 一括更新メソッドに受け取った値を渡す
		msgSttSvc.updateMessageStatus(msgSttForm);
		return "redirect:/dashboard";
	}

	/**
	 * ステータスメッセージの挿入
	 *
	 * @param msgSttForm
	 * @return ダッシュボード
	 *//*
		 * @PostMapping("message_status/input")
		 *
		 * @Transactional(readOnly = false) public String msgSttInput(
		 *
		 * @ModelAttribute("MessageStatus") MessageStatus msgStt, Model model) {
		 * msgSttSvc.messageStatusInput(msgStt); return "redirect:/dashboard"; }
		 */
}