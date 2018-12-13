package com.yinq.history.model;

import com.yinq.datamodel.JsonModel;

public class SleepStatisticsModel extends JsonModel {

	private String month;
	private int count;
	private float score;
	
	private int noonEarlyCount;
	private int noonNormalCount;
	private int noonLateCount;
	
	private int eveningEarlyCount;
	private int eveningNormalCount;
	private int eveningLateCount;
	
	
	public SleepStatisticsModel(String month) {
		this.setMonth(month);
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

	public int getNoonEarlyCount() {
		return noonEarlyCount;
	}

	public void setNoonEarlyCount(int noonEarlyCount) {
		this.noonEarlyCount = noonEarlyCount;
	}

	public int getNoonNormalCount() {
		return noonNormalCount;
	}

	public void setNoonNormalCount(int noonNormalCount) {
		this.noonNormalCount = noonNormalCount;
	}

	public int getNoonLateCount() {
		return noonLateCount;
	}

	public void setNoonLateCount(int noonLateCount) {
		this.noonLateCount = noonLateCount;
	}

	public int getEveningEarlyCount() {
		return eveningEarlyCount;
	}

	public void setEveningEarlyCount(int eveningEarlyCount) {
		this.eveningEarlyCount = eveningEarlyCount;
	}

	public int getEveningNormalCount() {
		return eveningNormalCount;
	}

	public void setEveningNormalCount(int eveningNormalCount) {
		this.eveningNormalCount = eveningNormalCount;
	}

	public int getEveningLateCount() {
		return eveningLateCount;
	}

	public void setEveningLateCount(int eveningLateCount) {
		this.eveningLateCount = eveningLateCount;
	}
}
