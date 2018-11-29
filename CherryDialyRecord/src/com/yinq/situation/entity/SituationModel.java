package com.yinq.situation.entity;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.JsonModel;
import com.yinq.datamodel.RespError;

public class SituationModel extends JsonModel {
	
	private SituationRecordModel record;
	private SituationDetModel det;
	
	public SituationModel() {
		// TODO Auto-generated constructor stub
	}
	
	public SituationModel(SituationRecordModel recordModel){
		record = recordModel;
	}

	public SituationRecordModel getRecord() {
		return record;
	}

	public void setRecord(SituationRecordModel record) {
		this.record = record;
	}

	public SituationDetModel getDet() {
		return det;
	}

	public void setDet(SituationDetModel det) {
		this.det = det;
	}
	
	public int save() {
		if (record == null || det == null) {
			return RespError.urlInvalidParamError;
		}
		
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.persist(record);
			
			session.persist(det);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			transaction.rollback();
			session.close();
			
			return RespError.databaseError;
		}
		return 0;
	}
	
	public boolean hasBeenExisted() {
		boolean hasBeenExisted = false;
		
		Session session = HibernateUtil.getSession();
		String sql = "select e from " + SituationRecordModel.class.getName()
				+ " e " + "where e.date=:date and e.type=:type";
		Query<SituationRecordModel> query = session.createQuery(sql);
		query.setParameter("date", record.getDate());
		query.setParameter("type", record.getType());
		
		String json = det.toJson();
		
		
		switch (record.getType()) {
		case 1:
		{
			//吃饭情况
			MealSituationModel mealSituationModel = (MealSituationModel) new MealSituationModel().fromJson(json);
			
			try {
				List<SituationRecordModel> records = query.getResultList();
				for (SituationRecordModel record : records) {
					MealSituationModel model = session.get(MealSituationModel.class, record.getId());

					if (model.getCode() == mealSituationModel.getCode()) {
						hasBeenExisted = true;
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session.close();
				hasBeenExisted = true;
			}
			session.close();
			
			break;
		}
		case 2:{
			//睡觉情况
			
			SleepSituationModel situationModel = (SleepSituationModel) new SleepSituationModel().fromJson(json);
			
			try {
				List<SituationRecordModel> records = query.getResultList();
				for (SituationRecordModel record : records) {
					SleepSituationModel model = session.get(SleepSituationModel.class, record.getId());
					
					if (model.getCode() == situationModel.getCode()) {
						hasBeenExisted = true;
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session.close();
				hasBeenExisted = true;
			}
			session.close();
			break;
		}
		case 3:{
			//兴趣学习情况
			InterestSituationModel situationModel = (InterestSituationModel) new InterestSituationModel().fromJson(json);
			
			try {
				List<SituationRecordModel> records = query.getResultList();
				for (SituationRecordModel record : records) {
					InterestSituationModel model = session.get(InterestSituationModel.class, record.getId());
					
					if (model.getCate().getCateId() == situationModel.getCate().getCateId()) {
						hasBeenExisted = true;
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session.close();
				hasBeenExisted = true;
			}
			session.close();
			break;
		}
		default:
			break;
		}
		return hasBeenExisted;
	}

}
