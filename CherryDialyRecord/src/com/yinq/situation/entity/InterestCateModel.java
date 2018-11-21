package com.yinq.situation.entity;





import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yinq.datamodel.HibernateProxyTypeAdapter;
import com.yinq.datamodel.JsonModel;

@Entity
@Table(name="interest_cate_table")
public class InterestCateModel extends JsonModel {
	
	private int cateId;
	private int cateCode;
	private String name;
	
	
	public InterestCateModel() {
		// TODO Auto-generated constructor stub
	}
	
	public InterestCateModel(InterestCateModel model){
		cateId = model.getCateId();
		cateCode = model.getCateCode();
		name = model.getName();
	}

	@Id
	@Column(name="cateId")
	public int getCateId() {
		return cateId;
	}

	public void setCateId(int id) {
		this.cateId = id;
	}

	@Column(name="cateCode")
	public int getCateCode() {
		return cateCode;
	}

	public void setCateCode(int cateCode) {
		this.cateCode = cateCode;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	protected Gson createGson() {
		// TODO Auto-generated method stub
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		return builder.create();
	}

	

	
	
}
