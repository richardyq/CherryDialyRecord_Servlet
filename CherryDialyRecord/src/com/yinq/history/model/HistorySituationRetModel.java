package com.yinq.history.model;

import java.util.ArrayList;



public class HistorySituationRetModel {

	private int count;
	private ArrayList<DailySituationModel> list;
	
	public HistorySituationRetModel() {
		// TODO Auto-generated constructor stub
		list = new ArrayList<DailySituationModel>();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<DailySituationModel> getList() {
		return list;
	}

	public void setList(ArrayList<DailySituationModel> list) {
		this.list = list;
	}
}
