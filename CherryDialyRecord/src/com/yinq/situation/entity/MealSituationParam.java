package com.yinq.situation.entity;

import com.yinq.datamodel.JsonModel;

public class MealSituationParam extends JsonModel {

	private String date;
	private int mealCode;
	private int speed;
	private int amount;
	private int feed;
	private String userId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getMealCode() {
		return mealCode;
	}
	public void setMealCode(int mealCode) {
		this.mealCode = mealCode;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getFeed() {
		return feed;
	}
	public void setFeed(int feed) {
		this.feed = feed;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
