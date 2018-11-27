package com.yinq.history.model;

import java.util.ArrayList;

import com.yinq.datamodel.JsonModel;


public class DailySituationModel extends JsonModel {
	
	private String date;
	
	private ArrayList<MealHistoryDetModel> mealSituationModels;
	private ArrayList<SleepHistoryDetModel> sleepSituationModels;
	private ArrayList<InterestHistoryDetModel> interestSituationModels;
	
	public DailySituationModel() {
		// TODO Auto-generated constructor stub
		mealSituationModels = new ArrayList<MealHistoryDetModel>();
		sleepSituationModels = new ArrayList<SleepHistoryDetModel>();
		interestSituationModels = new ArrayList<InterestHistoryDetModel>();
	}

	public ArrayList<MealHistoryDetModel> getMealSituationModels() {
		return mealSituationModels;
	}

	public void setMealSituationModels(ArrayList<MealHistoryDetModel> mealSituationModels) {
		this.mealSituationModels = mealSituationModels;
	}

	public ArrayList<SleepHistoryDetModel> getSleepSituationModels() {
		return sleepSituationModels;
	}

	public void setSleepSituationModels(ArrayList<SleepHistoryDetModel> sleepSituationModels) {
		this.sleepSituationModels = sleepSituationModels;
	}

	public ArrayList<InterestHistoryDetModel> getInterestSituationModels() {
		return interestSituationModels;
	}

	public void setInterestSituationModels(ArrayList<InterestHistoryDetModel> interestSituationModels) {
		this.interestSituationModels = interestSituationModels;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
