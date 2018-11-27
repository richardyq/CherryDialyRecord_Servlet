package com.yinq.history.model;

import com.yinq.situation.entity.SituationDetModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SituationRecordModel;

public class SituationHistoryDetModel {

	private String id;
	
	private String date;
	private String updateTime;
	private String userName;
	private String userId;
	
	public SituationHistoryDetModel() {
		// TODO Auto-generated constructor stub
	}
	
	public SituationHistoryDetModel(SituationModel situationModel) {
		SituationRecordModel recordModel = situationModel.getRecord();
		id = recordModel.getId();
		date = recordModel.getDate();
		updateTime = recordModel.getUpdateTime();
		userName = recordModel.getUser().getUserName();
		userId = recordModel.getUser().getUserId();
		
		makeDet(situationModel.getDet());
	}
	
	public void makeDet(SituationDetModel detModel) {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
