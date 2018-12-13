package com.yinq.funny.entity;


import com.yinq.datamodel.JsonModel;


public class FunnyRequestParam extends JsonModel {

	private int kidId;
	private String userId;
	private String date;
	private String content;
	
	private String startDate;
	private String endDate;
	private int startRow;
	private int rows;
	
	public FunnyRequestParam() {
		
	}
	
	public FunnyRequestParam(String userId, String content, String date, int kidId) {
		this.userId = userId;
		this.date = date;
		this.content = content;
		this.kidId = kidId;
	}
	
	

	public int getKidId() {
		return kidId;
	}

	public void setKidId(int kidId) {
		this.kidId = kidId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
}
