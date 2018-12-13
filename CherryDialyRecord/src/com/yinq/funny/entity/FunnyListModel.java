package com.yinq.funny.entity;

import java.util.List;

import com.yinq.datamodel.JsonModel;

public class FunnyListModel extends JsonModel {

	private int count;
	private List<FunnyModel> list;
	
	public FunnyListModel() {
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<FunnyModel> getList() {
		return list;
	}

	public void setList(List<FunnyModel> list) {
		this.list = list;
	}
}
