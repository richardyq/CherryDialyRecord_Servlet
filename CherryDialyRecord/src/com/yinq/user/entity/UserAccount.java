package com.yinq.user.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="user_account_table")

public class UserAccount extends JsonModel{
	
	private String userId;
	private String account;
	private String password;
	private String lastLoginTime;
	
	public UserAccount() {
	// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name="userAccount")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="lastLoginDateTime", nullable=true)
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
