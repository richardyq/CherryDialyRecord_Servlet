package com.yinq.history.model;

import java.util.ArrayList;

import com.yinq.datamodel.JsonModel;

public class InterestStatisticsModel  extends JsonModel{

	private String month;
	private int count;
	private float score;
	
	private ArrayList<InterestStatisticsDetModel> detModels;
	
	public InterestStatisticsModel(String month) {
		this.setMonth(month);
		detModels = new ArrayList<InterestStatisticsDetModel>();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public ArrayList<InterestStatisticsDetModel> getDetModels() {
		return detModels;
	}

	public void setDetModels(ArrayList<InterestStatisticsDetModel> detModels) {
		this.detModels = detModels;
	}
}
