package jp.co.tc.recruit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.entity.User.Authority;
import jp.co.tc.recruit.form.UserForm;
import jp.co.tc.recruit.repository.UserRepository;

@Service
public class UserService implements UserDetailsService/*, Comparator<User>*/ {
	@Autowired
	private UserRepository usrRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<User> findAllByOrderByUsername() {
		return usrRepo.findAllByOrderByUsername();
	}

	public List<User> findAllByOrderByAuthority() {
		return usrRepo.findAllByOrderByAuthority();
	}

	public List<User> findAllByOrderByStatusDesc() {
		return usrRepo.findAllByOrderByStatusDesc();
	}

	public User findById(int i) {
		return usrRepo.findById(i);
	}

	/*	public List<User> findByAuthority(Authority roleUser) {
			return usrRepo.findByAuthority(roleUser);
		}

		public List<User> findByStatus(int i) {
			return usrRepo.findByStatus(i);
		}*/

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
	public /*List<UserForm>*/void userMultipleUpdate(UserForm userFormList) {

		for (int i = 0; i < userFormList.getId().size(); i++) {
			int idByForm = Integer.parseInt((userFormList.getId().get(i)).toString());
			String usernameByForm = (userFormList.getUsername().get(i)).toString();
			String firstNameByForm = (userFormList.getFirstName().get(i)).toString();
			String lastNameByForm = (userFormList.getLastName().get(i)).toString();
			Authority authorityByForm = userFormList.getAuthority().get(i);
			Integer statusBooleanByForm = Integer.parseInt((userFormList.getStatusBoolean().get(i)).toString());
			/*String passwordByForm = (userFormList.getPassword().get(i)).toString();*/

			/*Integer idByDB = Integer.parseInt(((User)usrRepo.findById(idByForm)).getId().toString());*/
			User userInfo = usrRepo.findById(idByForm);
			String usernameByDB = userInfo.getUsername().toString();
			String firstNameByDB = userInfo.getFirstName().toString();
			String lastNameByDB = userInfo.getLastName().toString();
			String authorityByDB = userInfo.getAuthority().toString();
			Integer statusByDB = Integer.parseInt(userInfo.getStatus().toString());

			//入力値とDB値が異なる場合、それぞれ保存
			if (!(usernameByForm.equals(usernameByDB))) {
				userInfo.setUsername(usernameByForm);
				usrRepo.save(userInfo);
			}
			if (!(firstNameByForm.equals(firstNameByDB))) {
				userInfo.setFirstName(firstNameByForm);
				usrRepo.save(userInfo);
			}
			if (!(lastNameByForm.equals(lastNameByDB))) {
				userInfo.setLastName(lastNameByForm);
				usrRepo.save(userInfo);
			}
			if (authorityByForm.name() != authorityByDB) {
				userInfo.setAuthority(authorityByForm);
				usrRepo.save(userInfo);
			}
			if (statusBooleanByForm != statusByDB) {
				userInfo.setStatus(statusBooleanByForm);
				usrRepo.save(userInfo);
			}
		}
		/*		Comparator<User> user = Comparator.comparing(User::getUsername);
				userInfo.sort(user);

				return userFormList;*/
	}

	/**
	 * 社員マスタのcsv取込
	 *
	 */
	public List<String> userCsvImport(List<String> userList) {
		List<User> userInfo = new ArrayList<User>();
		List<String> message = new ArrayList<String>();

		//それぞれバリデーションチェック
		Pattern usernamePattern = Pattern.compile("^TC(-\\d{4})$");
		/*		Pattern lastNamePattern = Pattern.compile("\\p{Han}");
				Pattern firstNamePattern = Pattern.compile("\\p{Han}");*/
		/*Pattern authorityPattern = Pattern.compile("[01]{1}");*/

		/*2行目から値取得
		ユーザー名/姓/名/権限(0,1 → ROLE_ADMIN,ROLE_USER)をそれぞれ登録*/
		for (int k = 1; k <= (userList.size() / 4) - 1; k++) {
			User user = new User();
			for (int i = (4 * k); i <= ((4 * k) + 3); i++) {
				if (i % 4 == 0) {
					if (usernamePattern.matcher(userList.get(i)).matches() == false) {
						message.add((k+1) + "行目　ユーザー名はTC-0000形式で入力してください");
					} else {
						user.setUsername(userList.get(i));
						user.setStatus(1);
					}
				} else if (i % 4 == 1) {
					if (userList.get(i).length() >= 1 && userList.get(i).length() > 10 || userList.get(i).isEmpty()) {
						message.add((k+1) + "行目　姓は1文字以上10文字以下で入力してください");
					} else {
						user.setLastName(userList.get(i));
					}
				} else if (i % 4 == 2) {
					if (userList.get(i).length() >= 1 && userList.get(i).length() > 10 || userList.get(i).isEmpty()) {
						message.add((k+1) + "行目　名は1文字以上10文字以下で入力してください");
					} else {
						user.setFirstName(userList.get(i));
					}
				} else if (i % 4 == 3) {
					System.out.println(userList.get(i).length() == 2);
					System.out.println(userList.get(i).contains("0"));
					System.out.println(userList.get(i).contains("1"));
					if (userList.get(i).length() != 2 && (userList.get(i).contains("0") || userList.get(i).contains("1"))) {
						message.add((k+1) + "行目　権限は管理者「0」,一般「1」を入力してください");
					} else {
						if ((userList.get(i)).contains("1")) {
							user.setAuthority(Authority.ROLE_USER);
						} else {
							user.setAuthority(Authority.ROLE_ADMIN);
						}
					}
				}
			}
			userInfo.add(user);
		}
		if (message.isEmpty()) {
			usrRepo.saveAll(userInfo);
		} else {
			//DB登録しない
		}
		return message;
	}

	/**
	 * 社員マスタから検索
	 *
	 */
	public List<User> userSarch(UserForm userForm, String[] userArray) {
		List<User> sarchList = new ArrayList<User>();
		List<User> sarchUsername = null;
		List<User> sarchLastName = null;
		List<User> sarchFirstName = null;
		Integer sarchAuthorityAdmin = userForm.getSarchAuthorityAdmin();
		Integer sarchAuthorityUser = userForm.getSarchAuthorityUser();
		Integer sarchStatusBoolean = userForm.getSarchStatusBoolean();
		List<User> removeList = new ArrayList<User>();

		//デフォルトはusername順
		if (userForm.getSortLastName() == 2) {
			//ふりがな振ってから
		} else if (userForm.getSortFirstName() == 3) {
			//ふりがな振ってから
		} else if (userForm.getSortAuthority() == 4) {
			sarchList = usrRepo.findAllByOrderByAuthority();
		} else if (userForm.getSortStatus() == 5) {
			sarchList = usrRepo.findAllByOrderByStatusDesc();
		} else {
			sarchList = usrRepo.findAllByOrderByUsername();
		}

		/*OR条件であいまい検索*/
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

		/*権限チェックボックスに選択値があったとき*/
		if (sarchAuthorityAdmin == 1 && sarchAuthorityUser == 1) {
			//何もしない
		} else if (sarchAuthorityAdmin == 1) {
			removeList = usrRepo.findByAuthority(Authority.ROLE_USER);
			sarchList.removeAll(removeList);
		} else if (sarchAuthorityUser == 1) {
			removeList = usrRepo.findByAuthority(Authority.ROLE_ADMIN);
			sarchList.removeAll(removeList);
		}

		/*有効/無効ステータスにチェックが入ったとき*/
		if (sarchStatusBoolean == 1) {
			removeList = usrRepo.findByStatus(0);
			sarchList.removeAll(removeList);
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

	public void passwordRegist(String password, String username) {

		User userInfo = usrRepo.findByUsername(username);
		//パスワードをハッシュ化してセット
		userInfo.setPassword(passwordEncoder.encode(password));

		usrRepo.save(userInfo);
	}

}
