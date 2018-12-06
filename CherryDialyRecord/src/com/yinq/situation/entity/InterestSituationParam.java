package com.yinq.situation.entity;

import com.yinq.datamodel.JsonModel;

public class InterestSituationParam extends JsonModel{

	private String date;
	private int cateId;
	private int status;
	private String userId;
	private int kidId;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCateId() {
		return cateId;
	}
	public void setCateId(int cateId) {
		this.cateId = cateId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getKidId() {
		return kidId;
	}
	public void setKidId(int kidId) {
		this.kidId = kidId;
	}
	
	
}
