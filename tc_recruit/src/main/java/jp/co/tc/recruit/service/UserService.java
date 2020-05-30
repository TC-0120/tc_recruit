package jp.co.tc.recruit.service;

import java.util.ArrayList;
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

	public List<User> findAllByOrderByUsername() {
		return usrRepo.findAllByOrderByUsername();
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
	public void userMultipleUpdate(UserForm userFormList) {

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
			usrRepo.save(user);
		}
	}

	/**
	 * 社員マスタのcsv取込
	 *
	 */
	public void userCsvImport(List<String> userList) {
		List<User> userInfo = new ArrayList<User>();

		for (int k = 1; k <= (userList.size() / 4) - 1; k++) {
			User user = new User();
			for (int i = (4 * k); i <= ((4 * k) + 3); i++) {
				if (i % 4 == 0) {
					user.setUsername(userList.get(i));
					user.setStatus(1);
				} else if (i % 4 == 1) {
					user.setLastName(userList.get(i));
				} else if (i % 4 == 2) {
					user.setFirstName(userList.get(i));
				} else if (i % 4 == 3) {
					if ((userList.get(i)).contains("1")) {
						user.setAuthority(Authority.ROLE_USER);
					} else {
						user.setAuthority(Authority.ROLE_ADMIN);
					}
				}
			}
			userInfo.add(user);
		}
		usrRepo.saveAll(userInfo);
	}

	/**
	 * 社員マスタから検索
	 *
	 */
	public List<User> userSarch(String[] userArray) {
		List<User> sarchList = new ArrayList<User>();
		List<User> sarchUsername = null;
		List<User> sarchLastName = null;
		List<User> sarchFirstName = null;
		/*List<User> sarchAuthority = null;*/

		for (int i = 0; i < userArray.length; i++) {
			sarchUsername = usrRepo.findByUsernameLike("%" + userArray[i] + "%");
			sarchLastName = usrRepo.findByLastNameLike("%" + userArray[i] + "%");
			sarchFirstName = usrRepo.findByFirstNameLike("%" + userArray[i] + "%");

			/*sarchAuthority = usrRepo.findByAuthorityLike("%" + userArray[i] + "%");*/

			sarchList.addAll(sarchUsername);
			sarchList.addAll(sarchLastName);
			sarchList.addAll(sarchFirstName);
			/*sarchList.addAll(sarchAuthority);*/
		}
		/*重複データ削除*/
		for (int n = 0; n < sarchList.size() - 1; n++) {
			for (int k = sarchList.size() - 1; k > n; k--) {
				if (sarchList.get(n).getId().equals(sarchList.get(k).getId())) {
					sarchList.remove(k);
				}
			}
		}
		return sarchList;
	}

}
