package com.yinq.history;

import java.util.ArrayList;
import java.util.List;

import com.yinq.datamodel.RespError;
import com.yinq.history.StatisticsUtil;
import com.yinq.history.StatisticsUtil.StatisticsMonth;
import com.yinq.history.model.HistoryRequestParam;
import com.yinq.history.model.InterestStatisticsModel;
import com.yinq.history.model.MealStatisticsModel;
import com.yinq.history.model.SleepStatisticsModel;
import com.yinq.history.model.StatisticsModel;
import com.yinq.servlet.HttpRespModel;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SleepSituationModel;


public class StatisticsRequestUtil {

	public StatisticsRequestUtil() {
		
	}
	
	public HttpRespModel getTotalStatistics(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		if (param == null) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，你输入的参数不正确。");
			return respModel;
		}
		int type = param.getType();
		int kidId = param.getKidId(); 
		String startDate = param.getStartDate();
		String endDate = param.getEndDate();
		
		StatisticsUtil util = new StatisticsUtil();
		
		
		Long totalCount = util.totalCount(type, kidId, startDate, endDate);
		
		if (totalCount == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		
		Double averageScore = util.averageScore(type, kidId, startDate, endDate);
		if (averageScore == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		StatisticsModel model = new StatisticsModel();
		model.setCount(totalCount.intValue());
		model.setScore(averageScore.floatValue());
		
		respModel.setResult(model);
		return respModel;
	}
	
	public HttpRespModel getFirstDate(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		
		StatisticsUtil util = new StatisticsUtil();
		String date = util.firstRecordDate(param.getKidId(), param.getType());
		if (date == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		
		respModel.setResult(date);
		return respModel;
	}
	
	public HttpRespModel mealStatisticsInMonths(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		StatisticsUtil util = new StatisticsUtil();
		
		String endDate = util.firstRecordDate(param.getKidId(), 1);
		String startDate = util.lastRecordDate(param.getKidId(), 1);
		List<String> months = util.getRecordMonths(param.getKidId(), 1, startDate, endDate);
		if (util.getErrorCode() != 0) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		
		MealStatisticsUtil mealStatisticsUtil = new MealStatisticsUtil();
		ArrayList<MealStatisticsModel> models = new ArrayList<MealStatisticsModel>(); 
		for (String month : months) {
			MealStatisticsModel mealSituationModel = mealStatisticsUtil.getStatistics(month, param.getKidId());
			if (mealSituationModel != null) {
				models.add(mealSituationModel);
			}
			
		}
		respModel.setResult(models);
		return respModel;
	}
	
	public HttpRespModel sleepStatisticsInMonths(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		StatisticsUtil util = new StatisticsUtil();
		
		String endDate = util.firstRecordDate(param.getKidId(), 2);
		String startDate = util.lastRecordDate(param.getKidId(), 2);
		List<String> months = util.getRecordMonths(param.getKidId(), 2, startDate, endDate);
		if (util.getErrorCode() != 0) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		SleepStatisticsUtil sleepStatisticsUtil = new SleepStatisticsUtil();
		ArrayList<SleepStatisticsModel> models = new ArrayList<SleepStatisticsModel>(); 
		for (String month : months) {
			SleepStatisticsModel statisticsModel = sleepStatisticsUtil.getStatistics(month, param.getKidId());
			if (statisticsModel != null) {
				models.add(statisticsModel);
			}
			
		}
		respModel.setResult(models);
		return respModel;
	}
	
	public HttpRespModel interestStatisticsInMonth(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		StatisticsUtil util = new StatisticsUtil();
		
		String endDate = util.firstRecordDate(param.getKidId(), 3);
		String startDate = util.lastRecordDate(param.getKidId(), 3);
		List<String> months = util.getRecordMonths(param.getKidId(), 3, startDate, endDate);
		
		if (util.getErrorCode() != 0) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		
		InterestStatisticsUtil interestStatisticsUtil = new InterestStatisticsUtil();
		ArrayList<InterestStatisticsModel> models = new ArrayList<InterestStatisticsModel>();
		for (String month : months) {
			InterestStatisticsModel interestStatisticsModel = interestStatisticsUtil.getStatistics(month, param.getKidId());
			if (interestStatisticsModel != null) {
				models.add(interestStatisticsModel);
			}
		}
		
		respModel.setResult(models);
		return respModel;
		
	}
	
	public HttpRespModel testMethod(HistoryRequestParam param) {
		HttpRespModel respModel = new HttpRespModel();
		StatisticsUtil util = new StatisticsUtil();
		
		String endDate = util.firstRecordDate(param.getKidId(), param.getType());
		String startDate = util.lastRecordDate(param.getKidId(), param.getType());
		
		List<String> months = util.getRecordMonths(param.getKidId(), param.getType(), startDate, endDate);
		respModel.setResult(months);
		
		for (String month : months) {
			StatisticsMonth statisticsMonth = util.statisticsMonth(month);
			System.out.println("month start : " + statisticsMonth.getStartDate() + " end : " + statisticsMonth.getEndDate());;
		}
		
		return respModel;
	}
}
