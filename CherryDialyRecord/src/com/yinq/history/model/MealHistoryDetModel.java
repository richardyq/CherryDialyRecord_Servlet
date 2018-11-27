package com.yinq.history.model;

import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SituationDetModel;
import com.yinq.situation.entity.SituationModel;

public class MealHistoryDetModel extends SituationHistoryDetModel{

	private int code;
	private int speed;
	private int amount;
	private int feed;
	private float score;
	
	private String codeName;
	private String speedName;
	private String amountName;
	private String feedName;
	
	public MealHistoryDetModel() {
		// TODO Auto-generated constructor stub
	}
	
	public MealHistoryDetModel(SituationModel model){
		super(model);
	}
	
	@Override
	public void makeDet(SituationDetModel detModel) {
		// TODO Auto-generated method stub
		MealSituationModel mealDetModel = (MealSituationModel) detModel;
		
		code = mealDetModel.getCode();
		speed = mealDetModel.getSpeed();
		amount = mealDetModel.getAmount();
		feed = mealDetModel.getFeed();
		setScore(mealDetModel.getScore());
		
		switch (code) {
		case 0:
			codeName = "早餐";
			break;
		case 1:
			codeName = "午餐";
			break;
		case 2:
			codeName = "晚餐";
			break;
		default:
			break;
		}
		
		switch (speed) {
		case 0:
			speedName = "很慢";
			break;
		case 1:
			speedName = "一般";
			break;
		case 2:
			speedName = "较快";
			break;
		default:
			break;
		}
		
		switch (amount) {
		case 0:
			amountName = "很少";
			break;
		case 1:
			amountName = "一般";
			break;
		case 2:
			amountName = "较多";
			break;
		default:
			break;
		}
		
		switch (feed) {
		case 0:
			feedName = "全部喂";
			break;
		case 1:
			feedName = "喂一半";
			break;
		case 2:
			feedName = "自己吃";
			break;
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

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getSpeedName() {
		return speedName;
	}

	public void setSpeedName(String speedName) {
		this.speedName = speedName;
	}

	public String getAmountName() {
		return amountName;
	}

	public void setAmountName(String amountName) {
		this.amountName = amountName;
	}

	public String getFeedName() {
		return feedName;
	}

	public void setFeedName(String feedName) {
		this.feedName = feedName;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
}
