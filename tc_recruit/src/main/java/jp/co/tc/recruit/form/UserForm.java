package jp.co.tc.recruit.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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

        private Integer sarchAuthorityAdmin;
        private Integer sarchAuthorityUser;
        private Integer sarchStatusBoolean;
        private String[] sarchWord;
        private Integer sortUsername;
        private Integer sortFirstName;
        private Integer sortLastName;
        private Integer sortAuthority;
        private Integer sortStatus;

	    private MultipartFile multipartFile;

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
		sarchAuthorityAdmin = null;
		sarchAuthorityUser = null;
		sarchStatusBoolean = null;
		sortUsername = null;
		sortAuthority = null;
		sortStatus = null;
		sortFirstName = null;
		sortLastName = null;
	}

}
