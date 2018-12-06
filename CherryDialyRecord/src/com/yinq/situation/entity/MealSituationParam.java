package com.yinq.situation.entity;

import com.yinq.datamodel.JsonModel;

public class MealSituationParam extends JsonModel {

	private String date;
	private int code;
	private int speed;
	private int amount;
	private int feed;
	private String userId;
	private int kidId;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
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
	public int getKidId() {
		return kidId;
	}
	public void setKidId(int kidId) {
		this.kidId = kidId;
	}
}
