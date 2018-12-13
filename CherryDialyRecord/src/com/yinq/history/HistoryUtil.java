package com.yinq.history;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.history.model.DailySituationModel;
import com.yinq.history.model.InterestHistoryDetModel;
import com.yinq.history.model.MealHistoryDetModel;
import com.yinq.history.model.SleepHistoryDetModel;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SituationRecordModel;
import com.yinq.situation.entity.SleepSituationModel;


public class HistoryUtil {
	private int errorCode = 0;
	private String message = "";
	
	public HistoryUtil() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Long totalRecordsCount() {
		
		Session session = HibernateUtil.getSession();
		String sql = "select count(*) from " + SituationRecordModel.class.getName();
		Long count = new Long(0);
		Query<Long> query = session.createQuery(sql);
		
		try {
			count = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		
		return count;
	}
	
	public Long recordDateCount(int type, int kidId, String startDate, String endDate) {
		Session session = HibernateUtil.getSession();
		String sql = "select count(distinct e.date) from " + SituationRecordModel.class.getName() + " e";
		String conditionStr = " where e.kidId=:kidId";
		
		if (type > 0) {
			conditionStr += " and type=:type";
			
		}
		if (startDate != null && startDate.isEmpty() == false) {
			
			conditionStr += "  and date >=:startDate";
		}
		if (endDate != null && endDate.isEmpty() == false) {
			
			conditionStr += "  and date <=:endDate";
		}
		
		sql += conditionStr;
		Long count = new Long(0);
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		if (type > 0) {
			query.setParameter("type", type);
		}
		if (startDate != null && startDate.isEmpty() == false) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && endDate.isEmpty() == false) {
			query.setParameter("endDate", endDate);
		}
		try {
			count = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return count;
	}
	
	public ArrayList<SituationRecordModel> recordModels(int startRow, int rows){
		ArrayList<SituationRecordModel> recordModels = null;
		Session session = HibernateUtil.getSession();
		String sql = "select e from " + SituationRecordModel.class.getName() + " e order by e.updateTime desc";
		Query<SituationRecordModel> query = session.createQuery(sql);
		
		query.setFirstResult(startRow);
		query.setMaxResults(rows);
		try {
			List<SituationRecordModel> models = query.getResultList();
			recordModels = new ArrayList<SituationRecordModel>();
			if (models != null) {
				for (SituationRecordModel model : models) {
					SituationRecordModel recordModel = new SituationRecordModel(model);
					recordModels.add(recordModel);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return recordModels;
	}
	
	public ArrayList<String> recordDateList(int startRow, int rows, int type, String startDate, String endDate, int kidId){
		ArrayList<String> dates = null;
		Session session = HibernateUtil.getSession();
		
		String sql = "select distinct e.date from " + SituationRecordModel.class.getName() + " e";
		String conditionStr = " where kidId=:kidId";
		
		if (type > 0) {
			
			conditionStr += " and type=:type";
		}
		if (startDate != null && startDate.isEmpty() == false) {
			
			conditionStr += " and date >=:startDate";
		}
		if (endDate != null && endDate.isEmpty() == false) {
			
			conditionStr += " and date <=:endDate";
		}
		
		sql += conditionStr;
		sql += " order by e.date desc";
		Query<String> query = session.createQuery(sql);
		query.setFirstResult(startRow);
		query.setMaxResults(rows);
		query.setParameter("kidId", kidId);
		if (type > 0) {
			query.setParameter("type", type);
		}
		if (startDate != null && startDate.isEmpty() == false) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && endDate.isEmpty() == false) {
			query.setParameter("endDate", endDate);
		}
		try {
			List<String> dateList = query.getResultList();
			dates = new ArrayList<String>();
			for (String string : dateList) {
				dates.add(new String(string));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return dates;
	}
	
	public ArrayList<SituationRecordModel> recordsFormDate(String date, int kidId){
		if (date == null || date.isEmpty()) {
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你传人的日期参数错误。");
		}
		
		ArrayList<SituationRecordModel> records = null;
		Session session = HibernateUtil.getSession();
		String sql = "select e from " + SituationRecordModel.class.getName() + " e ";
		sql += "where e.date=:date and e.kidId=:kidId";
		Query<SituationRecordModel> query = session.createQuery(sql);
		query.setParameter("date", date);
		query.setParameter("kidId", kidId);
		try {
			List<SituationRecordModel> recordModels = query.getResultList();
			if (recordModels != null) {
				records = new ArrayList<SituationRecordModel>();
				for (SituationRecordModel model : recordModels) {
					records.add(new SituationRecordModel(model));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return records;
	}
	
	public DailySituationModel dailySituationModel(String date, int type, int kidId) {
		DailySituationModel dailySituationModel = null;
		ArrayList<SituationRecordModel> recordModels = recordsFormDate(date, kidId);
		
		if (recordModels == null) {
			return dailySituationModel;
		}
		
		Session session = HibernateUtil.getSession();
		dailySituationModel = new DailySituationModel();
		for (SituationRecordModel record : recordModels) {
			SituationModel situationModel = new SituationModel(record);
			try {
				switch (record.getType()) {
				case 1:{
					if (type != 0 && type != record.getType()) {
						break;
					}
					MealSituationModel model = session.get(MealSituationModel.class, record.getId());
					situationModel.setDet(new MealSituationModel(model));
					dailySituationModel.getMealSituationModels().add(new MealHistoryDetModel(situationModel));
					break;
				}
				case 2:{
					if (type != 0 && type != record.getType()) {
						break;
					}
					SleepSituationModel model = session.get(SleepSituationModel.class, record.getId());
					situationModel.setDet(new SleepSituationModel(model));
					dailySituationModel.getSleepSituationModels().add(new SleepHistoryDetModel(situationModel));
					break;
				}
				case 3:{
					if (type != 0 && type != record.getType()) {
						break;
					}
					InterestSituationModel model = session.get(InterestSituationModel.class, record.getId());
					situationModel.setDet(new InterestSituationModel(model));
					dailySituationModel.getInterestSituationModels().add(new InterestHistoryDetModel(situationModel));
					break;
				}
				default:
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		dailySituationModel.setDate(date);
		session.close();
		return dailySituationModel;
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
