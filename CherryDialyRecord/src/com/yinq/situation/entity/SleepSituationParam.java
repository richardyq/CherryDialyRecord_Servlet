package com.yinq.situation.entity;

import com.yinq.datamodel.JsonModel;

public class SleepSituationParam extends JsonModel {

	private String date;
	private String userId;
	
	private int code;
	private int status;
	
	public SleepSituationParam() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
