package com.yinq.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="user_info_table")
public class UserSummaryModel extends JsonModel{
	
	private String userId;
	private String userName;
	
	public UserSummaryModel() {
		// TODO Auto-generated constructor stub
	}
	
	public UserSummaryModel(UserSummaryModel aModel) {
		// TODO Auto-generated constructor stub
		userId = aModel.getUserId();
		userName = aModel.getUserName();
	}
	
	@Id
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
