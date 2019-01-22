package com.yinq.situation.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;


import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.InterestSituationParam;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.MealSituationParam;

import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SituationRecordModel;
import com.yinq.situation.entity.SleepSituationModel;
import com.yinq.situation.entity.SleepSituationParam;

public class SituationUtil {

	private int errorCode;
	private String message;
	
	public SituationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public SituationModel createMealSituationModel(MealSituationParam param) {

		setErrorCode(0);
		setMessage(null);
		
		SituationModel recordModel = null;
		
		//check input param
		if (param == null) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的参数不正确。");
			return recordModel;
		}
		
		if (param.getUserId() == null || param.getUserId().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的userId参数不正确。");
			return recordModel;
		}
		
		if (param.getKidId() == 0) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的kidId参数不正确。");
			return recordModel;
		}
		
		if (param.getDate() == null || param.getDate().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的日期参数不正确。");
			return recordModel;
		}
		

		SituationRecordModel record = new SituationRecordModel(param.getUserId()); 
		record.setDate(param.getDate());
		record.setType(1);
		record.setKidId(param.getKidId());
		
		recordModel = new SituationModel(record);
		MealSituationModel detModel = new MealSituationModel(param);
		detModel.setId(record.getId());
		recordModel.setDet(detModel);
		
		return recordModel;
	}
	
	public ArrayList<SituationModel> getMealSituationsFromDate(String date, int kidId){
		ArrayList<SituationModel> models = null;
		setErrorCode(0);
		setMessage(null);
		if (date == null || date.isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，您输入的日期参数不正确。");
			return models;
		}
		
		ArrayList<SituationRecordModel> recordModels = getSituationRecorModels(date, 1, kidId);
		if (recordModels == null || errorCode != 0) {
			return null;
		}
		models = new ArrayList<SituationModel>();
		Session session = HibernateUtil.getSession();
		try {
			for (SituationRecordModel record : recordModels) {
				 SituationModel model = new SituationModel(record);
				 MealSituationModel detModel = session.get(
						 MealSituationModel.class, record.getId());

				 MealSituationModel det = new 
						 MealSituationModel(detModel);
				 model.setDet(det);
				 
				 models.add(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
			session.close();
		}
		session.close();
		return models;
	}
	
	public ArrayList<SituationModel> getSleepSituationsFromDate(String date, int kidId) {
		ArrayList<SituationModel> models = null;
		setErrorCode(0);
		setMessage(null);
		if (date == null || date.isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，您输入的日期参数不正确。");
			return models;
		}
		
		ArrayList<SituationRecordModel> recordModels = getSituationRecorModels(date, 2, kidId);
		if (recordModels == null || errorCode != 0) {
			return null;
		}
		models = new ArrayList<SituationModel>();
		Session session = HibernateUtil.getSession();
		try {
			for (SituationRecordModel record : recordModels) {
				 SituationModel model = new SituationModel(record);
				 SleepSituationModel detModel = session.get(SleepSituationModel.class, record.getId());

				 SleepSituationModel det = new  SleepSituationModel(detModel);
				 model.setDet(det);
				 
				 models.add(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
			session.close();
		}
		session.close();
		return models;
	}
	
	public ArrayList<SituationModel> getInterestSituationFromDate(String date, int kidId) {
		ArrayList<SituationModel> models = null;
		setErrorCode(0);
		setMessage(null);
		if (date == null || date.isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，您输入的日期参数不正确。");
			return models;
		}
		
		ArrayList<SituationRecordModel> recordModels = getSituationRecorModels(date, 3, kidId);
		if (recordModels == null || errorCode != 0) {
			return null;
		}
		models = new ArrayList<SituationModel>();
		Session session = HibernateUtil.getSession();
		try {
			for (SituationRecordModel record : recordModels) {
				 SituationModel model = new SituationModel(record);
				 InterestSituationModel detModel = session.get(InterestSituationModel.class, record.getId());

				 InterestSituationModel det = new  InterestSituationModel(detModel);
				 model.setDet(det);
				 
				 models.add(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
			session.close();
		}
		session.close();
		return models;
	}
	
	public ArrayList<SituationRecordModel> getSituationRecorModels(String date, int type, int kidId) {
		setErrorCode(0);
		setMessage(null);
		
		if (kidId == 0) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的kidId错误。");
			return null;
		}
		
		ArrayList<SituationRecordModel> records = null;
		Session session = HibernateUtil.getSession();
		try {
			String sql = "select e from " + SituationRecordModel.class.getName() +
					" e where date=:date and type=:type and kidId=:kidId "+ "order by updateTime desc";
			Query<SituationRecordModel> query = session.createQuery(sql);
			query.setParameter("date", date);
			query.setParameter("type", type);
			query.setParameter("kidId", kidId);
			
			List<SituationRecordModel> models = query.getResultList();
			records = new ArrayList<SituationRecordModel>();
			for (SituationRecordModel situationRecordModel : models) {
				records.add(new SituationRecordModel(situationRecordModel));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
			session.close();
		}
		session.close();
		return records;
	}
	
	public SituationModel createSleepSituationModel(SleepSituationParam param) {
		SituationModel resultModel = null;
		setErrorCode(0);
		setMessage(null);
		
		//check input param
		if (param == null) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的参数不正确。");
			return resultModel;
		}
				
		if (param.getUserId() == null || param.getUserId().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的userId参数不正确。");
			return resultModel;
		}
		
		if (param.getKidId() == 0) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的kidId参数不正确。");
			return resultModel;
		}
				
		if (param.getDate() == null || param.getDate().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的日期参数不正确。");
			return resultModel;
		}
		
		SituationRecordModel record = new SituationRecordModel(param.getUserId()); 
		record.setDate(param.getDate());
		record.setType(2);
		record.setKidId(param.getKidId());
		resultModel = new SituationModel(record);
		SleepSituationModel model = new SleepSituationModel(param);
		model.setId(record.getId());
		resultModel.setDet(model);
		
		return resultModel;
	}
	
	public SituationModel createInterestSituationModel(InterestSituationParam param) {
		SituationModel resultModel = null;
		setErrorCode(0);
		setMessage(null);
		
		//check input param
		if (param == null) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的参数不正确。");
			return resultModel;
		}
						
		if (param.getUserId() == null || param.getUserId().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的userId参数不正确。");
			return resultModel;
		}
		
		if (param.getKidId() == 0) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的kidId参数不正确。");
			return resultModel;
		}
						
		if (param.getDate() == null || param.getDate().isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的日期参数不正确。");
			return resultModel;
		}
		
		SituationRecordModel record = new SituationRecordModel(param.getUserId()); 
		record.setDate(param.getDate());
		record.setType(3);
		record.setKidId(param.getKidId());
		resultModel = new SituationModel(record);
		InterestSituationModel model = new InterestSituationModel(param.getCateId(), param.getStatus());
		model.setId(record.getId());
		resultModel.setDet(model);
		
		return resultModel;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
