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
	 * 社員マスタの検索、表示
	 *
	 * @return 社員マスタメンテナンス画面
	 */
	@GetMapping("user")
	public String userUpdateInput(@ModelAttribute("User") UserForm userForm, Model model) {
		List<User> usrList = new ArrayList<User>();
		usrList = usrSvc.findAllByOrderByUsername();
		model.addAttribute("usrList", usrList);
		return "master_maintenance/user";
	}

	/**
	 * 社員の検索
	 *
	 * @param userForm
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/sarch")
	@Transactional(readOnly = true)
	public String userSarch(
			@ModelAttribute("User") UserForm userForm, Model model) {
		/*検索値の配列変換と該当Userの検索*/
		List<User> usrList = new ArrayList<User>();
		String[] userArray = Arrays.toString(userForm.getSarchWord())
				.replace("[", "").replace("]", "").split("( |　)+", 0);
		/*各チェックボックスにチェックなしで検索された場合*/
		if (userForm.getSarchAuthorityAdmin() == null) {
			userForm.setSarchAuthorityAdmin(0);
		}
		if (userForm.getSarchAuthorityUser() == null) {
			userForm.setSarchAuthorityUser(0);
		}
		if (userForm.getSarchStatusBoolean() == null) {
			userForm.setSarchStatusBoolean(0);
		}
		usrList = usrSvc.userSarch(userForm, userArray);

		/*検索値を所持してuser.htmlへ*/
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
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/update")
	@Transactional(readOnly = false)
	public String userUpdate(
			@ModelAttribute("User") UserForm userForm, Model model) {
		usrSvc.userMultipleUpdate(userForm);
		return "redirect:/maintenance/user";
	}

	/**
	 * 社員マスタのcsv取込
	 *
	 * @param msgSttForm
	 * @return 社員マスタメンテナンス画面
	 */
	@PostMapping("user/upload")
	@Transactional(readOnly = false)
	public String upload(@ModelAttribute("UploadUser") UserForm userFormCsv,
			@ModelAttribute("User") UserForm userForm,
			@RequestParam("userlist.csv") MultipartFile multipartFile, Model model) {
		Boolean res = false;

		try {
			File file = new File("C:\\" + multipartFile.getOriginalFilename());
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

			List<String> message = usrSvc.userCsvImport(userList);
			if (!(message.isEmpty())) {
				model.addAttribute("message", message);
				res = true;
				model.addAttribute("res", res);
			} else {
				model.addAttribute("res", res);
			}

			filereader.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}

		//検索フォームとフォームが異なるのでクリアな検索条件で値をセット
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
