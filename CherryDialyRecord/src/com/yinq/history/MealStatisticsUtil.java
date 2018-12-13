package com.yinq.history;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.history.StatisticsUtil.StatisticsMonth;
import com.yinq.history.model.HistoryRequestParam;
import com.yinq.history.model.MealStatisticsModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SituationRecordModel;

public class MealStatisticsUtil {
	private int errorCode = 0;
	private String message = "";
	
	public MealStatisticsUtil(){
		
	}
	
	public Long speedCount(int kidId, String month, int status) {
		Long count = null;
		errorCode = 0;
		message = null;
		
		StatisticsUtil util = new StatisticsUtil();
		StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		
		String sql = "select count(s) from " + MealSituationModel.class.getName() + " s, " 
				+ SituationRecordModel.class.getName() + " r where r.id=s.id and r.kidId=:kidId "
						+ " and r.date>=:startDate and r.date<=:endDate "
						+ " and s.speed=:speed";
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("startDate", statisticsMonth.getStartDate());
		query.setParameter("endDate", statisticsMonth.getEndDate());
		query.setParameter("speed", status);
		
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
	
	public Long feedCount(int kidId, String month, int status) {
		Long count = null;
		errorCode = 0;
		message = null;
		
		StatisticsUtil util = new StatisticsUtil();
		StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		
		String sql = "select count(s) from " + MealSituationModel.class.getName() + " s, " 
				+ SituationRecordModel.class.getName() + " r where r.id=s.id and r.kidId=:kidId "
						+ " and r.date>=:startDate and r.date<=:endDate "
						+ " and s.feed=:status";
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("startDate", statisticsMonth.getStartDate());
		query.setParameter("endDate", statisticsMonth.getEndDate());
		query.setParameter("status", status);
		
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
	
	public Long amountCount(int kidId, String month, int status) {
		Long count = null;
		errorCode = 0;
		message = null;
		
		StatisticsUtil util = new StatisticsUtil();
		StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		
		String sql = "select count(s) from " + MealSituationModel.class.getName() + " s, " 
				+ SituationRecordModel.class.getName() + " r where r.id=s.id and r.kidId=:kidId "
						+ " and r.date>=:startDate and r.date<=:endDate "
						+ " and s.amount=:status";
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("startDate", statisticsMonth.getStartDate());
		query.setParameter("endDate", statisticsMonth.getEndDate());
		query.setParameter("status", status);
		
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
	
	public MealStatisticsModel getStatistics(String month, int kidId) {
		MealStatisticsModel statisticsModel = new MealStatisticsModel(month);
		StatisticsUtil util = new StatisticsUtil();
		
		StatisticsUtil.StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		String startDate = statisticsMonth.getStartDate();
		String endDate = statisticsMonth.getEndDate();
		
		Long totalCount = util.totalCount(1, kidId, startDate, endDate);
		if (totalCount == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setCount(totalCount.intValue());
		
		Double averageScore = util.averageScore(1, kidId, startDate, endDate);
		if (averageScore == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setScore(averageScore.floatValue());
		//吃饭速度
		Long speedFast = speedCount(kidId, month, 2);
		if (speedFast == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setSpeedFast(speedFast.intValue());
		
		Long speedNormal = speedCount(kidId, month, 1);
		if (speedNormal == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setSpeedNormal(speedNormal.intValue());
		
		Long speedSlow = speedCount(kidId, month, 0);
		if (speedSlow == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setSpeedSlow(speedSlow.intValue());
		
		//是否喂饭
		Long feedGood = feedCount(kidId, month, 2);
		if (feedGood == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setFeedGood(feedGood.intValue());
				
		Long feedNormal = feedCount(kidId, month, 1);
		if (feedNormal == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setFeedNormal(feedNormal.intValue());
				
		Long feedLow = feedCount(kidId, month, 0);
		if (feedLow == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setFeedLow(feedLow.intValue());
		
		//饭量情况
		Long amountMuch = amountCount(kidId, month, 2);
		if (amountMuch == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setAmountMuch(amountMuch.intValue());
				
		Long amountNormal = amountCount(kidId, month, 1);
		if (amountNormal == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setAmountNormal(amountNormal.intValue());
				
		Long amountFew = amountCount(kidId, month, 0);
		if (amountFew == null) {
			setErrorCode(getErrorCode());
			setMessage(getMessage());
			return null;
		}
		statisticsModel.setAmountFew(amountFew.intValue());
		
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
