package com.yinq.history.model;

import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.SituationDetModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SleepSituationModel;

public class InterestHistoryDetModel extends SituationHistoryDetModel {
	
	private int cateId;
	private String cateName;
	private int score;
	private int status;
	private String statusName;

	public InterestHistoryDetModel() {
		// TODO Auto-generated constructor stub
	}
	 
	public InterestHistoryDetModel(SituationModel detModel){
		super(detModel);
		
		
	}
	
	@Override
	public void makeDet(SituationDetModel detModel) {
		// TODO Auto-generated method stub
		super.makeDet(detModel);
		
		InterestSituationModel interestSituationModel = (InterestSituationModel) detModel;
		cateId = interestSituationModel.getCate().getCateId();
		cateName = interestSituationModel.getCate().getName();
		status = interestSituationModel.getStatus();
		setScore(interestSituationModel.getScore());
		
		switch (status) {
		case 0:{
			setStatusName("表现很差");
			break;
		}
		case 1:{
			setStatusName("表现一般");
			break;
		}
		case 2:{
			setStatusName("表现很好");
			break;
		}
		default:
			break;
		}
	}
	

	public int getCateId() {
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
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

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
