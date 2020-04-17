package jp.co.tc.recruit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.tc.recruit.entity.User;
import jp.co.tc.recruit.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("1：" + username);

		if (username == null || username.isEmpty()) {
			throw new UsernameNotFoundException("");
		}
		User userInfo = userRepo.findByUsername(username);
		if (userInfo == null) {
			throw new UsernameNotFoundException("");
		}
		System.out.println("2：" + userInfo.getUsername());

		return userInfo;
	}
}
