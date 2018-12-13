package com.yinq.history.model;

import com.yinq.datamodel.JsonModel;

public class StatisticsModel extends JsonModel{

	private int count;
	private float score;
	
	public StatisticsModel() {
		
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
}
