package com.yinq.situation.entity;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinq.datamodel.HibernateProxyTypeAdapter;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="interest_situation_table")
public class InterestSituationModel extends JsonModel  {

	private String id;
	private InterestCateModel cate;
	private String date;
	private int status;
	private int score;
	
	
	public InterestSituationModel() {
		// TODO Auto-generated constructor stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
	}
	
	public InterestSituationModel(String date, int cateId, int status){
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		id = uuid;
		
		this.date = date;
		this.status = status;
		this.score = 3 + status;
		
		Session session = HibernateUtil.getSession();
		try {
			InterestCateModel cateModel = session.get(InterestCateModel.class, cateId);
			this.cate = cateModel;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.close();
		}
		session.close();
	}

	public InterestSituationModel(InterestSituationModel model){
		this.id = model.getId();
		this.score = model.getScore();
		this.status = model.status;
		this.date = model.getDate();
		
		InterestCateModel cateModel = new InterestCateModel();
		this.cate = cateModel;
		
	}

	@Id
	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="cateId", nullable = false)
	public InterestCateModel getCate() {
		return cate;
	}

	public void setCate(InterestCateModel cate) {
		this.cate = cate;
	}

	@Column(name="date")
	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
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
	
	@Override
	protected Gson createGson() {
		// TODO Auto-generated method stub
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		return builder.create();
	}
}
