package jp.co.tc.recruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.tc.recruit.constant.DeleteFlagConstant;
import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.repository.UserRepository;
import jp.co.tc.recruit.service.UserService;

/**
 * パスワード設定のコントローラー
 *
 *  @author TC-0117
 */
@Controller
@RequestMapping("/password")
public class PasswordController {

	@Autowired
	UserService usrSvc;

	@Autowired
	UserRepository usrRepos;

	@GetMapping()
	public String getPasswordSet(Model model) {
		return "password_setting";
	}

	/**
	 * DBにパスワード登録
	 *
	 * @param model
	 * @return ログイン画面
	 *
	 * 7/15 課題71　修正　湯澤
	 */
	@PostMapping("regist")
	public String PasswordRegist(@ModelAttribute("passwordRegist") UserForm userForm, Model model) {
		//パスワード
		String password = userForm.getPassword().get(0);
		//パスワード(確認用)
		String passwordConfirm = userForm.getPassword().get(1);
		String username = userForm.getUsername().get(0);
		String message = "入力内容が異なります";
		String message2 = "入力されたユーザー名が存在しません。";

		//パスワードの文字数制限を行う。
//		if (password.length() >= 4 && password.length() <= 8) {

			//パスワードとパスワード(確認用)の値が一致した場合
			if (password.equals(passwordConfirm)) {

				if (usrRepos.findByUsernameLikeAndDeleteFlag(username,DeleteFlagConstant.NOT_DELETED).isEmpty()) {
					model.addAttribute("message", message2);
					return "password_setting";

				} else {
					//入力された社員番号の社員データにパスワードを登録
					usrSvc.registPassword(password, username);
				}

				//パスワードとパスワード(確認用)の値が不一致だった場合
			} else {
				/* エラーメッセージを所持して、パスワード設定画面へ戻る */
				model.addAttribute("message", message);
				return "password_setting";
			}

		return "redirect:/login";

	}

}
