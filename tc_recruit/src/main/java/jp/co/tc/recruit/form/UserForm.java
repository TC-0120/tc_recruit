package jp.co.tc.recruit.form;

import java.util.List;

import jp.co.tc.recruit.entity.User.Authority;
import lombok.Data;

/**
 * ステータス変更フォームクラス
 *
 * 未使用
 */
@Data
public class UserForm {
        private List<Integer> id;
        private List<String> username;
        private List<String> firstName;
        private List<String> lastName;
        private List<String> password;
        private List<Authority> authority;
        private List<Integer> status;
        private String[] sarchWord;

	public UserForm() {
		id = null;
		username = null;
		firstName = null;
		lastName = null;
		password = null;
		authority = null;
		status = null;
		sarchWord = null;
	}

}
