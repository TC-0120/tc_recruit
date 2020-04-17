package jp.co.tc.recruit.Bean;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


public interface Authentication extends Principal, Serializable {
	// 1. 認可情報の getter
	  Collection<? extends GrantedAuthority> getAuthorities();

	  // 2. パスワードの getter
	  Object getCredentials();

	  Object getDetails();

	  // 3. ユーザー名の getter
	  Object getPrincipal();

	  boolean isAuthenticated();

	  void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

}
