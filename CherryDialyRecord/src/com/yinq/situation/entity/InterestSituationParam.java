package com.yinq.situation.entity;

import com.yinq.datamodel.JsonModel;

public class InterestSituationParam extends JsonModel{

	private String date;
	private int cateId;
	private int status;
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
	
	
}