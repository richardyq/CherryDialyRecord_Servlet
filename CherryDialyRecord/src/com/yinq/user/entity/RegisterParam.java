package com.yinq.user.entity;

import com.yinq.datamodel.JsonModel;

public class RegisterParam extends JsonModel{
	private String account;
    private String password;
    private String kidName;
    private String kidNickName;
    private String userName;
    private String relation;
    private String mobile;
    private String birthday;
    private int gender;

    public RegisterParam(){

    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKidName() {
		return kidName;
	}

	public void setKidName(String kidName) {
		this.kidName = kidName;
	}

	public String getKidNickName() {
		return kidNickName;
	}

	public void setKidNickName(String kidNickName) {
		this.kidNickName = kidNickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
}
