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
@Table(name="sleep_situation_table")
public class SleepSituationModel extends JsonModel {
	private String id;
	private String date;
	private int code;
	private int status;
	private int score;
	private UserSummaryModel user;
	private String updateTime;
	
	public SleepSituationModel() {
		// TODO Auto-generated constructor stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
	}
	
	public SleepSituationModel(SleepSituationParam param) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
		this.date = param.getDate();
		this.code = param.getCode();
		this.status = param.getStatus();
		this.score = 3 + status;
		
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
	
	public SleepSituationModel(SleepSituationModel model) {
		id = model.getId();
		date = model.getDate();
		code = model.getCode();
		status = model.getStatus();
		score = model.getScore();
		
		updateTime = model.getUpdateTime();
		UserSummaryModel user = new UserSummaryModel(model.getUser());
		this.user = user;
	}
	
	
	
	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="code")
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="score")
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
