package com.yinq.situation.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.user.entity.UserSummaryModel;

@Entity
@Table(name="situation_record_table")
public class SituationRecordModel {
	private String id;
	private String date;
	private UserSummaryModel user;
	private int type;
	private String updateTime;
	private int kidId;
	
	public SituationRecordModel() {
		// TODO Auto-generated constructor stub
		generalId();
	}
	
	public SituationRecordModel(String userId){
		
		fillUserModelWithUserId(userId);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		updateTime = format.format(new Date());
		generalId();
	}
	
	public SituationRecordModel(SituationRecordModel recordModel) {
		id = recordModel.getId();
		date = recordModel.getDate();
		type = recordModel.getType();
		updateTime = recordModel.getUpdateTime();
		
		user = new UserSummaryModel(recordModel.getUser());
	}
	
	private void generalId() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		setId(uuid);
	}
	
	private void fillUserModelWithUserId(String userId) {
		Session session = HibernateUtil.getSession();
		try {
			UserSummaryModel model = session.get(UserSummaryModel.class, userId);
			setUser(new UserSummaryModel(model));
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
	@Column(name="date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public UserSummaryModel getUser() {
		return user;
	}

	public void setUser(UserSummaryModel user) {
		this.user = user;
	}

	@Column(name="type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
}
