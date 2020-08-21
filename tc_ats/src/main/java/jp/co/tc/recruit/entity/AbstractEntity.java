package jp.co.tc.recruit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author TC-0120
 * 08/07
 *共通カラムの処理をスーパークラスで行う
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

	@Autowired
	//UserRepository userRepository;

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

	public Integer getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(Integer insertUser) {
		this.insertUser = insertUser;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 登録前処理
	 */
	@PrePersist
	public void prePersist() {
		// 登録日、更新日を設定
		Date date = new Date();
		insertDate = date;
		updateDate = date;
	}

	/**
     * 更新前処理
     */
    @PreUpdate
    public void preUpdate() {
    	//更新者IDを設定
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		//updateUser = (userRepository.findByUsername(principal.getUsername())).getId();
        // 更新日を設定
    	updateDate = new Date();
    }

}
