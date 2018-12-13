package com.yinq.history;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.history.StatisticsUtil.StatisticsMonth;
import com.yinq.history.model.InterestStatisticsDetModel;
import com.yinq.history.model.InterestStatisticsModel;
import com.yinq.situation.entity.InterestCateModel;
import com.yinq.situation.entity.InterestSituationModel;

import com.yinq.situation.entity.SituationRecordModel;
import com.yinq.situation.interest.InterestUtil;

public class InterestStatisticsUtil {

	private int errorCode = 0;
	private String message = "";
	
	List<InterestCateModel> cates;
	public InterestStatisticsUtil(){
		InterestUtil util = new InterestUtil();
		cates = util.allInterestList();
	}
	
	public Long interestCount(int kidId, String month, int status, int cateId) {
		Long count = null;
		errorCode = 0;
		message = null;
		
		StatisticsUtil util = new StatisticsUtil();
		StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		
		String sql = "select count(s) from " + InterestSituationModel.class.getName() + " s, " 
				+ SituationRecordModel.class.getName() + " r where r.id=s.id and r.kidId=:kidId "
						+ " and r.date>=:startDate and r.date<=:endDate "
						+ " and s.status=:status and s.cate.cateId=:cateId";
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("startDate", statisticsMonth.getStartDate());
		query.setParameter("endDate", statisticsMonth.getEndDate());
		query.setParameter("status", status);
		query.setParameter("cateId", cateId);
		
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
	
	public InterestStatisticsDetModel getInterestStatisticDet(int kidId, String month, int cateId) {
		InterestStatisticsDetModel detModel = new InterestStatisticsDetModel();
		errorCode = 0;
		message = null;
		Long goodCount = interestCount(kidId, month, 2, cateId);
		if (goodCount == null) {
			return null;
		}
		detModel.setGoodCount(goodCount.intValue());
		
		Long normalCount = interestCount(kidId, month, 1, cateId);
		if (normalCount == null) {
			return null;
		}
		detModel.setNormalCount(normalCount.intValue());
		
		Long lowCount = interestCount(kidId, month, 0, cateId);
		if (lowCount == null) {
			return null;
		}
		detModel.setLowCount(lowCount.intValue());
		
		
		return detModel;	
	}
	
	public InterestStatisticsModel getStatistics(String month, int kidId) {
		InterestStatisticsModel statisticsModel = new InterestStatisticsModel(month);
		StatisticsUtil util = new StatisticsUtil();
		
		StatisticsUtil.StatisticsMonth statisticsMonth = util.statisticsMonth(month);
		String startDate = statisticsMonth.getStartDate();
		String endDate = statisticsMonth.getEndDate();
		
		Long totalCount = util.totalCount(3, kidId, startDate, endDate);
		if (totalCount == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setCount(totalCount.intValue());
		
		Double averageScore = util.averageScore(3, kidId, startDate, endDate);
		if (averageScore == null) {
			setErrorCode(util.getErrorCode());
			setMessage(util.getMessage());
			return null;
		}
		statisticsModel.setScore(averageScore.floatValue());
		
		for (InterestCateModel interestCateModel : cates) {
			InterestStatisticsDetModel detModel = getInterestStatisticDet(kidId, month, interestCateModel.getCateId());
			detModel.setCateName(interestCateModel.getName());
			statisticsModel.getDetModels().add(detModel);
		}
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
