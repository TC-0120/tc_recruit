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
import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.entity.SelectionStatusDetail;
import jp.co.tc.recruit.form.MessageStatusForm;
import jp.co.tc.recruit.repository.MessageStatusRepository;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.service.UserService;

/**
 * マスタメンテナンスのコントローラー
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
	 * @return マスタメンテナンス一覧
	 */
	@GetMapping()
	public String getMaintenance(Model model) {
		return "/setting";
	}

	/**
	 * ダッシュボードのマスタ情報の検索、表示
	 *
	 * @param model
	 * @return メッセージステータスメンテナンス画面
	 */
	@GetMapping("message_status")
	public String msgSttUpdateInput(
			@ModelAttribute("MessageStatus") MessageStatusForm msgSttForm,
			Model model) {
		List<SelectionStatus> slcStt;
		List<SelectionStatusDetail> slcSttDtl;
		List<MessageStatus> msgSttList;
		msgSttList = msgSttSvc.findAllByOrderByStatusMessageId();
		slcStt = slcSttSvc.findAll();
		slcSttDtl = slcSttDtlSvc.findAll();

		/*ダッシュボードでの表示名取得*/
		model.addAttribute("msgSttList", msgSttList);
		/*プルダウン：ステータス*/
		model.addAttribute("slcStt", slcStt);
		/*プルダウン：詳細ステータス*/
		model.addAttribute("slcSttDtl", slcSttDtl);
		return "setting/message_status";
	}

	/**
	 * ステータスメッセージの一括更新
	 *
	 * @param msgSttForm
	 * @return ダッシュボード
	 */
	@PostMapping("message_status/update")
	@Transactional(readOnly = false)
	public String msgSttUpdateComplete(
			@ModelAttribute("MessageStatus") MessageStatusForm msgSttForm, Model model) {
		msgSttSvc.messageStatusUpdate(msgSttForm);
		return "redirect:/dashboard";
	}

	/**
	 * ステータスメッセージの挿入
	 *
	 * @param msgSttForm
	 * @return ダッシュボード
	 */
	@PostMapping("message_status/input")
	@Transactional(readOnly = false)
	public String msgSttInput(
			@ModelAttribute("MessageStatus") MessageStatus msgStt, Model model) {
		msgSttSvc.messageStatusInput(msgStt);
		return "redirect:/dashboard";
	}
}