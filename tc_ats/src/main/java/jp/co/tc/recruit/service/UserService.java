package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.repository.UserRepository;

@Service
public class UserService implements UserDetailsService/* , Comparator<User> */ {
	@Autowired
	private UserRepository usrRepo;
	@Autowired
	PasswordEncoder passwordEncoder;

//	public List<User> findAllByOrderByUsername() {
//		return usrRepo.findAllByOrderByUsername();
//	}
//
//	public List<User> findAllByOrderByAuthority() {
//		return usrRepo.findAllByOrderByAuthority();
//	}
//
//	public List<User> findAllByOrderByStatusDesc() {
//		return usrRepo.findAllByOrderByStatusDesc();
//	}
//
//	public User findById(int i) {
//		return usrRepo.findById(i);
//	}
//
//	/*
//	 * public List<User> findByAuthority(Authority roleUser) { return
//	 * usrRepo.findByAuthority(roleUser); }
//	 *
//	 * public List<User> findByStatus(int i) { return usrRepo.findByStatus(i); }
//	 */
//
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("");
		}
		User userInfo = usrRepo.findByUsername(username);//usrRepo.findByUsername(username)
		if (userInfo == null) {
			throw new UsernameNotFoundException("");
		}
		return userInfo;
	}
//
//	/**
//	 * 社員マスタから検索
//	 *
//	 */
//	public List<User> sarchUser(UserForm userForm, String[] userArray) {
//		List<User> sarchList = new ArrayList<User>();
//		List<User> sarchUsername = null;
//		List<User> sarchLastName = null;
//		List<User> sarchFirstName = null;
//		Integer sarchAuthorityAdmin = userForm.getSarchAuthorityAdmin();
//		Integer sarchAuthorityUser = userForm.getSarchAuthorityUser();
//		Integer sarchStatusBoolean = userForm.getSarchStatusBoolean();
//		List<User> removeList = new ArrayList<User>();
//
//		/* 検索窓に入力された検索ワードをOR条件であいまい検索 */
//		/* 検索窓での検索可能項目は、社員番号・姓・名のみ */
//		for (int i = 0; i < userArray.length; i++) {
//			sarchUsername = usrRepo.findByUsernameLike("%" + userArray[i] + "%");
//			sarchLastName = usrRepo.findByLastNameLike("%" + userArray[i] + "%");
//			sarchFirstName = usrRepo.findByFirstNameLike("%" + userArray[i] + "%");
//			/* sarchAuthority = usrRepo.findByAuthorityLike("%" + userArray[i] + "%"); */
//
//			sarchList.addAll(sarchUsername);
//			sarchList.addAll(sarchLastName);
//			sarchList.addAll(sarchFirstName);
//
//			/* 重複データ削除 */
//			for (int n = 0; n < sarchList.size() - 1; n++) {
//				for (int k = sarchList.size() - 1; k > n; k--) {
//					if (sarchList.get(n).getId().equals(sarchList.get(k).getId())) {
//						sarchList.remove(k);
//					}
//				}
//			}
//
//			/* 権限チェックボックスに選択値があったとき */
//			/* 両方にチェックが入った場合 */
//			if (sarchAuthorityAdmin == 1 && sarchAuthorityUser == 1) {
//				// 何もしない
//
//				/* 管理者にチェックが入った場合、一般ユーザーを表示リストからを除く */
//			} else if (sarchAuthorityAdmin == 1) {
//				removeList = usrRepo.findByAuthority(Authority.ROLE_USER);
//				sarchList.removeAll(removeList);
//
//				/* 一般にチェックが入った場合、管理者ユーザーを表示リストからを除く */
//			} else if (sarchAuthorityUser == 1) {
//				removeList = usrRepo.findByAuthority(Authority.ROLE_ADMIN);
//				sarchList.removeAll(removeList);
//			}
//
//			/* 無効ステータスを除くにチェックが入ったとき表示リストから無効ユーザーを除く */
//			if (sarchStatusBoolean == 1) {
//				removeList = usrRepo.findByStatus(0);
//				sarchList.removeAll(removeList);
//			}
//
//			// ソート
//			// 並び替えのルール
//			// 初期値はUsername昇順
//			// 権限ボタン押下で 優先順位1：authority昇順 優先順位2：username昇順
//			// 有効/無効ボタン押下で 優先順位1：status昇順 優先順位2：username昇順
//
//			// 権限ボタンを押下した場合
//			if (userForm.getSortAuthority() == 4) {
//				Comparator<User> authorityComparator = Comparator
//						.comparing(User::getAuthority, Comparator.reverseOrder()).thenComparing(User::getUsername);
//				sarchList.sort(authorityComparator);
//
//				// 有効/無効ボタンを押下した場合
//			} else if (userForm.getSortStatus() == 5) {
//				Comparator<User> statusComparator = Comparator.comparing(User::getStatus, Comparator.reverseOrder())
//						.thenComparing(User::getUsername);
//				sarchList.sort(statusComparator);
//
//				// 社員番号を押下した場合と、ソート条件がない場合
//			} else {
//				Comparator<User> usernameComparator = Comparator.comparing(User::getUsername);
//				sarchList.sort(usernameComparator);
//			}
//		}
//		return sarchList;
//	}
//
//	/**
//	 * 社員マスタの一括更新
//	 *
//	 */
//	public void multipleUpdateUser(UserForm userForm) {
//
//		for (int i = 0; i < userForm.getId().size(); i++) {
//			// フォームから取得した値
//			int idByForm = Integer.parseInt((userForm.getId().get(i)).toString());
//			String usernameByForm = (userForm.getUsername().get(i)).toString();
//			String firstNameByForm = (userForm.getFirstName().get(i)).toString();
//			String lastNameByForm = (userForm.getLastName().get(i)).toString();
//			Authority authorityByForm = userForm.getAuthority().get(i);
//			Integer statusBooleanByForm = Integer.parseInt((userForm.getStatusBoolean().get(i)).toString());
//
//			// DBから取得した値
//			User userInfo = usrRepo.findById(idByForm);
//			String usernameByDB = userInfo.getUsername().toString();
//			String firstNameByDB = userInfo.getFirstName().toString();
//			String lastNameByDB = userInfo.getLastName().toString();
//			String authorityByDB = userInfo.getAuthority().toString();
//			Integer statusByDB = Integer.parseInt(userInfo.getStatus().toString());
//
//			// 入力値とDB値が異なる場合、それぞれ保存
//			if (!(usernameByForm.equals(usernameByDB))) {
//				userInfo.setUsername(usernameByForm);
//				usrRepo.save(userInfo);
//			}
//			if (!(firstNameByForm.equals(firstNameByDB))) {
//				userInfo.setFirstName(firstNameByForm);
//				usrRepo.save(userInfo);
//			}
//			if (!(lastNameByForm.equals(lastNameByDB))) {
//				userInfo.setLastName(lastNameByForm);
//				usrRepo.save(userInfo);
//			}
//			if (authorityByForm.name() != authorityByDB) {
//				userInfo.setAuthority(authorityByForm);
//				usrRepo.save(userInfo);
//			}
//			if (statusBooleanByForm != statusByDB) {
//				userInfo.setStatus(statusBooleanByForm);
//				usrRepo.save(userInfo);
//			}
//		}
//	}
//
//	/**
//	 * 社員マスタのcsv取込
//	 *
//	 */
//	public List<String> importUserCsv(List<String> userList) {
//		List<User> importUser = new ArrayList<User>();
//		List<String> message = new ArrayList<String>();
//		boolean usernameExist = false;
//
//		// 社員番号のバリデーション
//		Pattern usernamePattern = Pattern.compile("^TC(-\\d{4})$");
//
//		/*
//		 * ファイルデータを2行目から値取得 バリデーションチェックを通過(入力エラーなし)した場合 ユーザー名/姓/名/権限(0,1 →
//		 * ROLE_ADMIN,ROLE_USER)をそれぞれ登録
//		 */
//		for (int k = 1; k <= (userList.size() / 4) - 1; k++) {
//			User user = new User();
//			for (int i = (4 * k); i <= ((4 * k) + 3); i++) {
//				// ユーザ―名入力値判定
//				if (i % 4 == 0) {
//					// 同一のユーザー名の存在チェック
//					String usernameByCsvfile = userList.get(i);
//					usernameExist = usrRepo.existsByUsername(usernameByCsvfile);
//					if (usernameExist == true) {
//						message.add((k + 1) + "行目　同一のユーザー名が存在します");
//					}
//					// usernameの入力チェック
//					if (usernamePattern.matcher(userList.get(i)).matches() == false) {
//						message.add((k + 1) + "行目　ユーザー名はTC-0000形式で入力してください");
//					} else {
//						user.setUsername(userList.get(i));
//						user.setStatus(1);
//					}
//
//				// 姓入力値判定
//				} else if (i % 4 == 1) {
//					if (userList.get(i).length() >= 1 && userList.get(i).length() >= 10 || userList.get(i).isEmpty()) {
//						message.add((k + 1) + "行目　姓は1文字以上10文字以下で入力してください");
//					} else {
//						user.setLastName(userList.get(i));
//					}
//
//				// 名入力値判定
//				} else if (i % 4 == 2) {
//					if (userList.get(i).length() >= 1 && userList.get(i).length() >= 10 || userList.get(i).isEmpty()) {
//						message.add((k + 1) + "行目　名は1文字以上10文字以下で入力してください");
//					} else {
//						user.setFirstName(userList.get(i));
//					}
//
//				// 権限入力値判定
//				} else if (i % 4 == 3) {
//					//改行コードを含むため、「0\r」で2文字以外はfalse
//					//0を含まない　または　1を含まない場合はfalse
//					if (userList.get(i).length() != 2 && userList.isEmpty() && userList.equals(null)
//							&& (userList.get(i).contains("0") || userList.get(i).contains("1"))) {
//						message.add((k + 1) + "行目　権限は管理者「0」,一般「1」を入力してください");
//					} else {
//						//権限欄に1を入力した場合、ROLE_USERを登録
//						if ((userList.get(i)).contains("1")) {
//							user.setAuthority(Authority.ROLE_USER);
//
//						//権限欄に0を入力した場合、ROLE_ADMINを登録
//						} else if ((userList.get(i)).contains("0")) {
//							user.setAuthority(Authority.ROLE_ADMIN);
//
//						// ロジック修正したい
//						//権限欄に0と1以外の1桁の整数(2~9)を入力した場合
//						} else {
//							message.add((k + 1) + "行目　権限は管理者「0」,一般「1」を入力してください");
//						}
//					}
//
//					/* 改行コード消したい */
//					/*
//					 * String authorityStr = userList.get(i).replace("\r", ""); if (authorityStr !=
//					 * null) { int authorityInt = Integer.parseInt(authorityStr);
//					 *
//					 * if (authorityInt != 0 || authorityInt != 1) { message.add((k + 1) +
//					 * "行目　権限は管理者「0」,一般「1」を入力してください"); } else { if (authorityInt == 1) {
//					 * user.setAuthority(Authority.ROLE_USER); } else {
//					 * user.setAuthority(Authority.ROLE_ADMIN); } } } else { message.add((k + 1) +
//					 * "行目　権限は管理者「0」,一般「1」を入力してください"); }
//					 */
//
//				}
//			}
//			importUser.add(user);
//		}
//		if (message.isEmpty()) {
//			usrRepo.saveAll(importUser);
//		} else {
//			// DB登録しない
//		}
//		return message;
//	}
//
//	/**
//	 * パスワード登録
//	 *
//	 */
//	public void registPassword(String password, String username) {
//		User userInfo = usrRepo.findByUsername(username);
//		// ハッシュ化したパスワードを挿入
//		userInfo.setPassword(passwordEncoder.encode(password));
//		usrRepo.save(userInfo);
//	}
//
//	/**
//	 * 一件ずつ新規登録
//	 *
//	 */
//	public List<String> registUser(User user) {
//		List<String> message = new ArrayList<String>();
//		// バリデーション
//		Pattern usernamePattern = Pattern.compile("^TC(-\\d{4})$");
//		boolean usernameExist = usrRepo.existsByUsername(user.getUsername());
//		if (usernameExist == true) {
//			message.add("同一のユーザー名が存在します");
//		}
//		// usernameの入力チェック
//		if (usernamePattern.matcher(user.getUsername()).matches() == false) {
//			message.add("ユーザー名はTC-0000形式で入力してください");
//		} else {
//			user.setUsername(user.getUsername());
//			user.setStatus(1);
//		}
//		// 姓入力値判定
//		if (user.getLastName().length() >= 1 && user.getLastName().length() >= 10 || user.getLastName().isEmpty()) {
//			message.add("姓は1文字以上10文字以下で入力してください");
//		} else {
//			user.setLastName(user.getLastName());
//		}
//		// 名入力値判定
//		if (user.getFirstName().length() >= 1 && user.getFirstName().length() >= 10 || user.getFirstName().isEmpty()) {
//			message.add("名は1文字以上10文字以下で入力してください");
//		} else {
//			user.setFirstName(user.getFirstName());
//		}
//
//		// 権限入力値判定
//		try {
//			if (user.getAuthority().equals(null)) {
//				// catchの中で処理
//			} else {
//				user.setAuthority(user.getAuthority());
//			}
//		} catch (NullPointerException e) {
//			message.add("権限を選択してください");
//		}
//
//		// バリデーションエラーなければ登録
//		if (message.isEmpty()) {
//			usrRepo.save(user);
//		} else {
//			// DB登録しない
//		}
//		return message;
//	}
}
