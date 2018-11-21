package com.yinq.situation.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinq.datamodel.HibernateProxyTypeAdapter;
import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="meal_situation_table")
public class MealSituationModel extends JsonModel{
	
	private String mealId;
	private String date;
	private int mealCode;
	private int speed;
	private int amount;
	private int feed;
	private float score;
	
	
	public MealSituationModel() {
		// TODO Auto-generated constructor stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		mealId = uuid;
	}
	
	public MealSituationModel(String date, int code, int speed, int amount, int feed) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		mealId = uuid;
		this.date = date;
		mealCode = code;
		this.speed = speed;
		this.amount = amount;
		this.feed = feed;
		
		score = (float) (2.0 + (speed + amount + feed)/2.0);
	}

	@Id
	@Column(name="mealId")
	public String getMealId() {
		return mealId;
	}


	public void setMealId(String mealId) {
		this.mealId = mealId;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}

	@Column(name="speed")
	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
		score = (float) (2.0 + (speed + amount + feed)/2.0);
	}

	@Column(name="amount")
	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name="feed")
	public int getFeed() {
		return feed;
	}

	public void setFeed(int feed) {
		this.feed = feed;
	}

	@Column(name="mealCode")
	public int getMealCode() {
		return mealCode;
	}

	public void setMealCode(int mealCode) {
		this.mealCode = mealCode;
	}

	@Column(name="score", nullable=true)
	public float getScore() {
//		if (score == 0.0) {
//			score = (float) (2.0 + (speed + amount + feed)/2.0);
//		}
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	
}
