package com.yinq.funny.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.JsonModel;
import com.yinq.user.entity.UserSummaryModel;

@Entity
@Table(name="funny_table")
public class FunnyModel extends JsonModel {

	private String id;
	private String content;
	private String date;
	private String updateTime;
	private int kidId;
	private UserSummaryModel userSummaryModel;
	
	public FunnyModel() {
		generalId();
	}
	
	public FunnyModel(FunnyRequestParam param) {
		generalId();
		fillUserModelWithUserId(param.getUserId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		updateTime = format.format(new Date());
		this.content = param.getContent();
		this.kidId = param.getKidId();
		this.date = param.getDate();
	}
	
	public FunnyModel(FunnyModel model) {
		UserSummaryModel summaryModel = new UserSummaryModel(model.getUserSummaryModel());
		userSummaryModel = summaryModel;
		id = model.getId();
		updateTime = model.updateTime;
		this.content = model.getContent();
		this.kidId = model.getKidId();
		this.date = model.getDate();
	}
	
	private void generalId() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		setId(uuid);
	}
	
	private void fillUserModelWithUserId(String userId) {
		Session session = HibernateUtil.getSession();
		try {
			UserSummaryModel model = session.get(UserSummaryModel.class, userId);
			setUserSummaryModel(new UserSummaryModel(model));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		session.close();
	}

	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column(name="updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="kidId")
	public int getKidId() {
		return kidId;
	}

	public void setKidId(int kidId) {
		this.kidId = kidId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public UserSummaryModel getUserSummaryModel() {
		return userSummaryModel;
	}

	public void setUserSummaryModel(UserSummaryModel userSummaryModel) {
		this.userSummaryModel = userSummaryModel;
	}
	
	
}
