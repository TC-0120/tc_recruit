package jp.co.tc.recruit.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.multipart.MultipartFile;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.service.SelectionStatusDetailService;
import jp.co.tc.recruit.service.SelectionStatusService;
import jp.co.tc.recruit.service.UserService;

/**
 * マスタメンテナンス機能のコントローラー
 *
 * @author TC-0117
 */
@Controller
@RequestMapping("/maintenance")
public class MasterMaintenanceController {

	@Autowired
	SelectionStatusService slcSttSvc;
	@Autowired
	SelectionStatusDetailService slcSttDtlSvc;
	//@Autowired
	//MessageStatusService msgSttSvc;
	//@Autowired
	//MessageStatusRepository msgSttRepo;
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
		return "master_maintenance";
	}

	/**
	 * 社員マスタの検索、表示
	 *
	 * @param userForm
	 * @param model
	 * @return 社員マスタメンテナンス画面
	 */
	@GetMapping("user")
	public String userUpdateInput(@ModelAttribute("User") UserForm userForm, Model model) {
		//全件取得、社員番号順
		List<User> usrList = new ArrayList<User>();
		usrList = usrSvc.findAllByOrderByUsername();
		model.addAttribute("usrList", usrList);
		return "master_maintenance/user";
	}

	/**
	 * 社員の検索
	 *
	 * @param userForm
	 * @param model
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/sarch")
	@Transactional(readOnly = true)
	public String userSarch(
			@ModelAttribute("User") UserForm userForm, Model model) {
		/*検索窓に入力された検索ワードのみ、String配列に変換する*/
		List<User> usrList = new ArrayList<User>();
		String[] userArray = Arrays.toString(userForm.getSarchWord())
				.replace("[", "").replace("]", "").split("( |　)+", 0);
		/*検索条件の各チェックボックスにチェックなしで検索された場合、チェックボックスoff状態(0)に設定する*/
		if (userForm.getSarchAuthorityAdmin() == null) {
			userForm.setSarchAuthorityAdmin(0);
		}
		if (userForm.getSarchAuthorityUser() == null) {
			userForm.setSarchAuthorityUser(0);
		}
		if (userForm.getSarchStatusBoolean() == null) {
			userForm.setSarchStatusBoolean(0);
		}

		/* 社員検索メソッドに検索条件を渡す */
		/*　userArray　=　検索窓に入力された条件*/
		/* userForm = その他条件 */
		usrList = usrSvc.sarchUser(userForm, userArray);

		/*　検索窓に入力された検索値を所持してuser.htmlへ(その他の検索データはuserFormに格納されている)*/
		/* 検索ワードごとに半角スペースを空けて表示 */
		String userArrayStr = String.join(" ", userArray);

		/*検索結果*/
		model.addAttribute("usrList", usrList);
		/*検索値*/
		model.addAttribute("userArray", userArrayStr);
		return "master_maintenance/user";
	}

	/**
	 * 社員マスタの一括更新
	 *
	 * @param userForm
	 * @param model
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/update")
	@Transactional(readOnly = false)
	public String userUpdate(
			@ModelAttribute("User") UserForm userForm, Model model) {
		//一括変更メソッドに受け取った社員情報を渡す
		usrSvc.multipleUpdateUser(userForm);
		return "redirect:/maintenance/user";
	}

	/**
	 * 社員マスタのcsv取込
	 *
	 * @param userFormCsv
	 * @param userForm
	 * @param multipartFile
	 * @param model
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/upload")
	@Transactional(readOnly = false)
	public String upload(@ModelAttribute("UploadUser") UserForm userFormCsv,
			@ModelAttribute("User") UserForm userForm,
			@RequestParam("userlist.csv") MultipartFile multipartFile, Model model) {
		List<String> message = new ArrayList<String>();

		try {
			//Cドライブ直下の取得ファイルの名称を取得する
			File file = new File("C:\\" + multipartFile.getOriginalFilename());
			//取得したファイル名のファイルデータの読み込みを行う
			FileReader filereader = new FileReader(file);
			int n;
			String str = null;
			StringBuilder user = new StringBuilder();
			StringBuilder stringBuilder = new StringBuilder();

			while ((n = filereader.read()) != -1) {
				str = String.valueOf((char) n);
				user = stringBuilder.append(str);
			}

			//読み込んだデータを改行で区切ってList化
			String[] userArray = user.toString().split("[\\n,]", 0);
			List<String> userList = Arrays.asList(userArray);

			//csv取込メソッドにファイルデータを渡し、エラーメッセージを取得する
			message = usrSvc.importUserCsv(userList);

			filereader.close();

		} catch (FileNotFoundException e) {
			message.add("ファイルが見つかりませんでした");
		} catch (IOException e) {
			message.add("取込に失敗しました");
		}

		//エラーメッセージがあればモデルに格納
		if (!(message.isEmpty())) {
			model.addAttribute("message", message);
		}

		//検索フォームとフォームが異なるので検索条件を初期化(検索条件なし)
		List<User> usrList = usrSvc.findAllByOrderByUsername();
		userForm.setSarchWord(null);
		userForm.setSarchAuthorityAdmin(0);
		userForm.setSarchAuthorityUser(0);
		userForm.setSarchStatusBoolean(0);
		model.addAttribute("userForm", userForm);
		model.addAttribute("usrList", usrList);

		return "master_maintenance/user";
	}

	/**
	 * 社員登録
	 *
	 * @param user
	 * @param userForm
	 * @param model
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/regist")
	@Transactional(readOnly = false)
	public String registUser(@ModelAttribute("RegistUser") User user,
			@ModelAttribute("User") UserForm userForm,
			Model model) {
		//社員登録メソッドに入力された内容を渡し、エラーメッセージを取得する
		List<String> messageForRegistUser = usrSvc.registUser(user);

		//エラーメッセージがあればモデルに格納
		if (!(messageForRegistUser.isEmpty())) {
			model.addAttribute("messageForRegistUser", messageForRegistUser);
		}

		//検索フォームとフォームが異なるので検索条件を初期化(検索条件なし)
		List<User> usrList = usrSvc.findAllByOrderByUsername();
		userForm.setSarchWord(null);
		userForm.setSarchAuthorityAdmin(0);
		userForm.setSarchAuthorityUser(0);
		userForm.setSarchStatusBoolean(0);
		model.addAttribute("userForm", userForm);
		model.addAttribute("usrList", usrList);

		return "master_maintenance/user";
	}

	/**
	 * 社員マスタのソート
	 *
	 * @param userForm
	 * @return 社員マスタメンテナンス画面
	 *//*
		@PostMapping("user/sort")
		@Transactional(readOnly = false)
		public String userSort(
			@ModelAttribute("User") UserForm userForm, Model model) {
		List<User> usrList = new ArrayList<User>();

		if(userForm.getSortUsername() == 1) {
			usrList = usrSvc.findAllByOrderByUsername();
		} else if(userForm.getSortLastName() == 2) {
			//ふりがな振ってから
		} else if(userForm.getSortFirstName() == 3) {
			//ふりがな振ってから
		} else if(userForm.getSortAuthority() == 4) {
			usrList = usrSvc.findAllByOrderByAuthority();
		} else if(userForm.getSortStatus() == 5) {
			usrList = usrSvc.findAllByOrderByStatusDesc();
		}

		//ユーザーフォームで受け取ったデータのみ表示する
		for (int n = 0; n < usrList.size(); n++) {
			for (int k = 0; k < userForm.getId().size(); k++) {
				if (usrList.get(n).getId().equals(userForm.getId().get(k))) {
					User user = usrSvc.findById(k);
					usrList.add(user);
				}
			}
		}

		model.addAttribute("usrList", usrList);
		return "master_maintenance/user";
		}
		*/
}
