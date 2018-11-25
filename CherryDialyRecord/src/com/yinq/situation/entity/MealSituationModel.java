package com.yinq.situation.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;



@Entity
@Table(name="meal_situation_table")
public class MealSituationModel extends SituationDetModel{
	
	private String id;
	
	private int code;
	private int speed;
	private int amount;
	private int feed;
	private float score;
	
	
	
	public MealSituationModel() {
		// TODO Auto-generated constructor stub
		
	}
	
	
	
	public MealSituationModel(MealSituationModel aModel){
		id = aModel.getId();
		code = aModel.getCode();
		
		speed = aModel.getSpeed();
		feed = aModel.getFeed();
		amount = aModel.getAmount();
		score = aModel.getScore();
	}
	
	public MealSituationModel(MealSituationParam param){
		code = param.getCode();
		
		speed = param.getSpeed();
		feed = param.getFeed();
		amount = param.getAmount();
		score = (float) (2.0 + (speed + amount + feed)/2.0);
	}


	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	

	@Column(name="speed")
	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
		
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

	@Column(name="code")
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Column(name="score", nullable=true)
	public float getScore() {

		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	

	
	
}
