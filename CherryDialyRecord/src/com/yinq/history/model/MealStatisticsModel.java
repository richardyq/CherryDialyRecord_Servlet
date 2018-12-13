package com.yinq.history.model;

import com.yinq.datamodel.JsonModel;

public class MealStatisticsModel extends JsonModel {

	private String month;
	private int count;
	private float score;
	
	private int speedFast;
	private int speedNormal;
	private int speedSlow;
	
	private int feedGood;
	private int feedNormal;
	private int feedLow;
	
	private int amountMuch;
	private int amountNormal;
	private int amountFew;
	
	public MealStatisticsModel(String month) {
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

	public int getSpeedFast() {
		return speedFast;
	}

	public void setSpeedFast(int speedFast) {
		this.speedFast = speedFast;
	}

	public int getSpeedNormal() {
		return speedNormal;
	}

	public void setSpeedNormal(int speedNormal) {
		this.speedNormal = speedNormal;
	}

	public int getSpeedSlow() {
		return speedSlow;
	}

	public void setSpeedSlow(int speedSlow) {
		this.speedSlow = speedSlow;
	}

	public int getFeedGood() {
		return feedGood;
	}

	public void setFeedGood(int feedGood) {
		this.feedGood = feedGood;
	}

	public int getFeedNormal() {
		return feedNormal;
	}

	public void setFeedNormal(int feedNormal) {
		this.feedNormal = feedNormal;
	}

	public int getFeedLow() {
		return feedLow;
	}

	public void setFeedLow(int feedLow) {
		this.feedLow = feedLow;
	}

	public int getAmountMuch() {
		return amountMuch;
	}

	public void setAmountMuch(int amountMuch) {
		this.amountMuch = amountMuch;
	}

	public int getAmountNormal() {
		return amountNormal;
	}

	public void setAmountNormal(int amountNormal) {
		this.amountNormal = amountNormal;
	}

	public int getAmountFew() {
		return amountFew;
	}

	public void setAmountFew(int amountFew) {
		this.amountFew = amountFew;
	}
	
	
}
