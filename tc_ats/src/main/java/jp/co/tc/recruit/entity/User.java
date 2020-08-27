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
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
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
	@SequenceGenerator(name = "user_sequence", sequenceName = "xxtc_user_id_seq", allocationSize = 1)
	private Integer id;

	@Column(nullable = false, unique = true)
	@NotNull
	private String username;

	@Column(name = "email_address", nullable = false)
	private String emailAddress;

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

	@Column(name = "login_date")
	private Date loginDate;

	/**
	 * 登録者ID
	 */
	@Column(name = "insert_user")
	private Integer insertUser;

	/**
	 * 登録日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_date")
	private Date insertDate;

	/**
	 * 更新者ID
	 */
	@Column(name = "update_user")
	private Integer updateUser;

	/**
	 * 更新日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;

	@Column(name = "delete_flag")
	private Integer deleteFlag;

	@PrePersist
	public void prePersist() {
		//登録社ID・更新者IDを設定
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//UserDetails principal = (UserDetails) auth.getPrincipal();
		//insertUser = (userRepository.findByUsername(principal.getUsername())).getId();
		//updateUser = insertUser;
		deleteFlag = 0;
		// 登録日、更新日を設定
		Date date = new Date();
		insertDate = date;
		updateDate = date;
	}

	@PreUpdate
    public void preUpdate() {
    	//更新者IDを設定
    	//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//UserDetails principal = (UserDetails) auth.getPrincipal();
		//updateUser = (userRepository.findByUsername(principal.getUsername())).getId();
        // 更新日を設定
    	updateDate = new Date();
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