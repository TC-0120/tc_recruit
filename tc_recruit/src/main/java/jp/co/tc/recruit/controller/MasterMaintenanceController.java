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
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.tc.recruit.entity.MessageStatus;
import jp.co.tc.recruit.entity.SelectionStatus;
import jp.co.tc.recruit.entity.SelectionStatusDetail;
import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.form.MessageStatusForm;
import jp.co.tc.recruit.form.MultipartFile;
import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.repository.MessageStatusRepository;
import jp.co.tc.recruit.service.MessageStatusService;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.service.UserService;

/**
 * マスタメンテナンスのコントローラー
 */
@Controller
@RequestMapping("/maintenance")
public class MasterMaintenanceController {

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
		return "/master_maintenance";
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
		/*修正予定 findAllByOrderBySelectionStatusIdAscSelectionStatusDetailIdAsc*/
		msgSttList = msgSttSvc.findAllByOrderByStatusMessageId();
		slcStt = slcSttSvc.findAll();
		slcSttDtl = slcSttDtlSvc.findAll();

		/*ダッシュボードでの表示名取得*/
		model.addAttribute("msgSttList", msgSttList);
		/*プルダウン：ステータス*/
		model.addAttribute("slcStt", slcStt);
		/*プルダウン：詳細ステータス*/
		model.addAttribute("slcSttDtl", slcSttDtl);
		return "master_maintenance/message_status";
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

	/**
	 * 社員マスタの検索、表示
	 *
	 * @return 社員マスタメンテナンス画面
	 */
	@GetMapping("user")
	public String userUpdateInput(
			@ModelAttribute("User") UserForm userForm, Model model) {
		List<User> usrList;
		usrList = usrSvc.findAllByOrderByUsername();
		model.addAttribute("usrList", usrList);
		return "master_maintenance/user";
	}

	/**
	 * 社員マスタの一括更新
	 *
	 * @param msgSttForm
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/update")
	@Transactional(readOnly = false)
	public String userUpdate(
			@ModelAttribute("User") UserForm userForm, Model model) {
		usrSvc.userUpdate(userForm);
		return "redirect:/maintenance/user";
	}

	/**
	 * 社員マスタの一括更新
	 *
	 * @param msgSttForm
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("sample/upload")
	public String upload(@RequestParam("upload_file") MultipartFile multipartFile, Model model) {
		System.out.println(multipartFile
		        .getOriginalFilename());
		/* model.addAttribute("originalFilename", multipartFile
		        .getOriginalFilename());*/

	    return "redirect:/maintenance/user";
	}

}
