package jp.co.tc.recruit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * セキュリティ設定を無視するリクエスト設定
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/images/**",
				"/css/**",
				"/js/**",
				"/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/","/login")
				.permitAll()
				//("/","/login")以外は認証が必要
				.anyRequest().authenticated()
				.and()
				//ログインしていないとき
				.formLogin()
				//以下に飛ばす
				.loginProcessingUrl("/login")
				//ログインページのUrl
				.loginPage("/login")
				/*//ログイン失敗時のUrl
				.failureUrl("/login")*/
				//認証を行うカラム
				.usernameParameter("username")
				.passwordParameter("password")
				//              .failureUrl("/login-error")
				.defaultSuccessUrl("/dashboard")
				.permitAll()
				.and()
				.logout()
				// ログアウトでログインページに戻る
				.logoutSuccessUrl("/login")
				// セッションを破棄する
				.invalidateHttpSession(true)
				.permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//DBによる独自認証を行う
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}