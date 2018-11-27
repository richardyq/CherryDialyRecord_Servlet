package com.yinq.history.model;

import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SituationDetModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SleepSituationModel;

public class SleepHistoryDetModel extends SituationHistoryDetModel {

	private int code;
	private int status;
	private int score;
	
	private String codeName;
	private String statusName;
	
	public SleepHistoryDetModel() {
		// TODO Auto-generated constructor stub
	}
	
	public SleepHistoryDetModel(SituationModel model){
		super(model);
	}
	
	@Override
	public void makeDet(SituationDetModel detModel) {
		// TODO Auto-generated method stub
		super.makeDet(detModel);
		
		SleepSituationModel sleepDetModel = (SleepSituationModel) detModel;
		code = sleepDetModel.getCode();
		status = sleepDetModel.getStatus();
		score = sleepDetModel.getScore();
		
		switch (code) {
		case 0:
		{
			codeName = "午觉";
			break;
		}
		case 1:{
			codeName = "晚上";
		}
		default:
			break;
		}
		
		switch (status) {
		case 0:{
			statusName = "睡得很晚，没睡";
			break;
		}
		case 1:{
			statusName = "睡得较晚";
			break;
		}
		case 2:{
			statusName = "正常时间入睡";
			break;
		}
		default:
			break;
		}
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
