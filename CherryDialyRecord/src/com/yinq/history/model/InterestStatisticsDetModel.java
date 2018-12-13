package com.yinq.history.model;

import com.yinq.datamodel.JsonModel;

public class InterestStatisticsDetModel extends JsonModel {

	private String cateName;
	private int goodCount;
	private int normalCount;
	private int lowCount;
	
	public InterestStatisticsDetModel(){
		
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public int getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}

	public int getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(int normalCount) {
		this.normalCount = normalCount;
	}

	public int getLowCount() {
		return lowCount;
	}

	public void setLowCount(int lowCount) {
		this.lowCount = lowCount;
	}
}
