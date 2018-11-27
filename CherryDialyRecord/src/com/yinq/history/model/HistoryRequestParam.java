package com.yinq.history.model;

import com.yinq.datamodel.JsonModel;

public class HistoryRequestParam extends JsonModel{

	private int startRow;
	private int rows;
	private int type;
	private String startDate;
	private String endDate;
	
	public HistoryRequestParam() {
		// TODO Auto-generated constructor stub
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

	public int getType() {
		return type;
	}

	public void setCode(int type) {
		this.type = type;
	}
}
