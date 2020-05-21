package jp.co.tc.recruit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.entity.User.Authority;
import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository usrRepo;
	User user = new User();

	public List<User> findAllByOrderByUsername(){
		return  usrRepo.findAllByOrderByUsername();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("");
		}
		User userInfo = usrRepo.findByUsername(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("");
		}
		return userInfo;
	}

	/**
	 * 社員マスタの一括更新
	 *
	 */
	public void userUpdate(UserForm userFormList) {
		System.out.println(userFormList.getId().size());

		for (int i = 0; i < userFormList.getId().size(); i++) {
			Integer idByForm = Integer.parseInt((userFormList.getId().get(i)).toString());
			String usernameByForm = (userFormList.getUsername().get(i)).toString();
			String firstNameByForm = (userFormList.getFirstName().get(i)).toString();
			String lastNameByForm = (userFormList.getLastName().get(i)).toString();
			Authority authorityByForm = userFormList.getAuthority().get(i);
			Integer statusByForm = Integer.parseInt((userFormList.getStatus().get(i)).toString());
			String passwordByForm = (userFormList.getPassword().get(i)).toString();

			//入力値がある場合、それぞれ保存
			if (idByForm != null) {
				user.setId(idByForm);
			}
			if (usernameByForm != null) {
				user.setUsername(usernameByForm);
			}
			if (firstNameByForm != null) {
				user.setFirstName(firstNameByForm);
			}
			if (lastNameByForm != null) {
				user.setLastName(lastNameByForm);
			}
			if (passwordByForm != null) {
				user.setPassword(passwordByForm);
			}
			if (authorityByForm != null) {
				user.setAuthority(authorityByForm);
			}
			if (statusByForm != null) {
				user.setStatus(statusByForm);
			}
			usrRepo.saveAndFlush(user);
		}
	}
}
