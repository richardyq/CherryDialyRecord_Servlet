package com.yinq.history;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.history.StatisticsUtil.StatisticsMonth;
import com.yinq.history.model.SleepStatisticsModel;
import com.yinq.situation.entity.SituationRecordModel;
import com.yinq.situation.entity.SleepSituationModel;

public class SleepStatisticsUtil {
	private int errorCode = 0;
	private String message = "";
	
	public SleepStatisticsUtil(){
		
	}
	
	public Long statusCount(int kidId, String month, int status, int code) {
		Long count = null;
		errorCode = 0;
		message = null;
		
		StatisticsUtil util = new StatisticsUtil();
		StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		
		String sql = "select count(s) from " + SleepSituationModel.class.getName() + " s, " 
				+ SituationRecordModel.class.getName() + " r where r.id=s.id and r.kidId=:kidId "
						+ " and r.date>=:startDate and r.date<=:endDate "
						+ " and s.status=:status and s.code=:code";
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("startDate", statisticsMonth.getStartDate());
		query.setParameter("endDate", statisticsMonth.getEndDate());
		query.setParameter("status", status);
		query.setParameter("code", code);
		
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
	
	public SleepStatisticsModel getStatistics(String month, int kidId) {
		SleepStatisticsModel statisticsModel = new SleepStatisticsModel(month);
		StatisticsUtil util = new StatisticsUtil();
		
		StatisticsUtil.StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		String startDate = statisticsMonth.getStartDate();
		String endDate = statisticsMonth.getEndDate();
		
		Long totalCount = util.totalCount(2, kidId, startDate, endDate);
		if (totalCount == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setCount(totalCount.intValue());
		
		Double averageScore = util.averageScore(2, kidId, startDate, endDate);
		if (averageScore == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setScore(averageScore.floatValue());
		
		//午睡情况
		Long noonEarly = statusCount(kidId, month, 2, 0);
		if (noonEarly == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setNoonEarlyCount(noonEarly.intValue());
		
		Long noonNormal = statusCount(kidId, month, 1, 0);
		if (noonNormal == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setNoonNormalCount(noonNormal.intValue());
		
		Long noonLate = statusCount(kidId, month, 0, 0);
		if (noonLate == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setNoonLateCount(noonLate.intValue());
		
		//晚上情况
		Long eveningEarly = statusCount(kidId, month, 2, 1);
		if (eveningEarly == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setEveningEarlyCount(eveningEarly.intValue());
		
		Long eveningNormal = statusCount(kidId, month, 1, 1);
		if (eveningNormal == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setEveningNormalCount(eveningNormal.intValue());
		
		Long eveningLate = statusCount(kidId, month, 0, 1);
		if (eveningLate == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setEveningLateCount(eveningLate.intValue());
		return statisticsModel;
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
