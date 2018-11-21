package com.yinq.situation.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;


import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.JsonModel;
import com.yinq.user.entity.UserSummaryModel;

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
	
	private UserSummaryModel user;
	private String updateTime;
	
	
	public MealSituationModel() {
		// TODO Auto-generated constructor stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		mealId = uuid;
	}
	
	
	public MealSituationModel(MealSituationParam param) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		mealId = uuid;
		this.date = param.getDate();
		mealCode = param.getMealCode();
		this.speed = param.getSpeed();
		this.amount = param.getAmount();
		this.feed = param.getFeed();
		
		score = (float) (2.0 + (speed + amount + feed)/2.0);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		updateTime = format.format(new Date());
		
		Session session = HibernateUtil.getSession();
		try {
			UserSummaryModel userSummaryModel = session.get(UserSummaryModel.class, param.getUserId());
			this.user = userSummaryModel;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.close();
		}
		session.close();
	}
	
	public MealSituationModel(MealSituationModel aModel){
		mealId = aModel.getMealId();
		mealCode = aModel.getMealCode();
		date = aModel.getDate();
		
		speed = aModel.getSpeed();
		feed = aModel.getFeed();
		amount = aModel.getAmount();
		score = aModel.getScore();
		
		updateTime = aModel.getUpdateTime();
		UserSummaryModel user = new UserSummaryModel(aModel.getUser());
		this.user = user;
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

		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="userId", nullable = false)
	public UserSummaryModel getUser() {
		return user;
	}

	public void setUser(UserSummaryModel user) {
		this.user = user;
	}

	@Column(name="updatetime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
