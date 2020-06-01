package jp.co.tc.recruit.form;

import java.util.List;

import jp.co.tc.recruit.entity.User.Authority;
import lombok.Data;

/**
 * ユーザーフォームクラス
 *
 */
@Data
public class UserForm {
        private List<Integer> id;
        private List<String> username;
        private List<String> firstName;
        private List<String> lastName;
        private List<String> password;
        private List<Authority> authority;
        private List<Integer> authorityInt;
        private List<Integer> status;
        private List<Integer> statusBoolean;
        private String[] sarchWord;

	public UserForm() {
		id = null;
		username = null;
		firstName = null;
		lastName = null;
		password = null;
		authority = null;
		authorityInt = null;
		status = null;
		statusBoolean = null;
		sarchWord = null;
	}

}
