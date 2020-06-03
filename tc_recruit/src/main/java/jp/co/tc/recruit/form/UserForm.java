package jp.co.tc.recruit.form;

import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import jp.co.tc.recruit.entity.User.Authority;
import lombok.Data;

/**
 * ユーザーフォームクラス
 *
 */
@Data
public class UserForm {
		@Id
        private List<Integer> id;

        @NotNull
    	@Pattern(regexp = "^TC(-\\d{4})$")
        private List<String> username;

        @Size(min = 1, max = 10)
        private List<String> firstName;

        @Size(min = 1, max = 10)
        private List<String> lastName;

        private List<String> password;

        private List<Authority> authority;

    	@Max(1)
        private List<Integer> authorityInt;

        private List<Integer> status;

        @Max(1)
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
