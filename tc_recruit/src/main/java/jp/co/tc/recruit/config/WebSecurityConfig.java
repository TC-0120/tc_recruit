package jp.co.tc.recruit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import jp.co.tc.recruit.Bean.SessionExpiredDetectingLoginUrlAuthenticationEntryPoint;

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
				.antMatchers("/", "/login", "/login?**", "dashboard", "/password", "/password/regist")
				.permitAll()
				.antMatchers("/maintenance", "/setting", "/maintenance/**", "/setting/**").hasAuthority("ROLE_ADMIN")
				//上記以外は認証が必要
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
				.permitAll()
		.and()
		.exceptionHandling()
		// 通常のRequestとAjaxを両方対応するSessionTimeout用
		.authenticationEntryPoint(authenticationEntryPoint())
		// csrfはsessionがないと動かない。SessionTimeout時にPOSTすると403 Forbiddenを必ず返してしまうため、
		// MissingCsrfTokenExceptionの時はリダイレクトを、それ以外の時は通常の扱いとする。
		.accessDeniedHandler(accessDeniedHandler());

	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//DBによる独自認証を行う
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint("/login");
    }

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				if (accessDeniedException instanceof MissingCsrfTokenException) {
					authenticationEntryPoint().commence(request, response, null);
				} else {
					new AccessDeniedHandlerImpl().handle(request, response, accessDeniedException);
				}
			}
		};
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
;
	}

}