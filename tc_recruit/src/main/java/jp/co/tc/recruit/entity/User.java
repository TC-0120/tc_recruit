package jp.co.tc.recruit.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "XXTC_USER")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	public enum Authority {
		ROLE_USER, ROLE_ADMIN
	};

	@Id
	@Column(nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	private Integer id;

	@Column(nullable = false, unique = true)
	@NotNull
	private String username;

	@Column(name = "first_name", nullable = false, unique = true)
	private String firstName;

	@Column(name = "last_name", nullable = false, unique = true)
	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Authority authority;

	/*	private Integer authorityId;*/

	@Column(nullable = false)
	private Integer status;
	
	@Column(name = "email_address")
	private String emailAddress;

@Column(name = "login_date")
	private Date loginDate;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersistflag() {
		//削除フラグを指定
		deleteFlag = 0;
	}

	@Override
	//エンティティを権限リストに追加
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(authority.toString()));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}