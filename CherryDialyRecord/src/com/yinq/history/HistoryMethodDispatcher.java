package com.yinq.history;
import com.yinq.datamodel.RespError;
import com.yinq.history.model.DailySituationModel;
import com.yinq.history.model.HistoryRequestParam;
import com.yinq.servlet.HttpRespModel;
import com.yinq.servlet.MethodDispatcher;



public class HistoryMethodDispatcher implements MethodDispatcher {

	
	@Override
	public HttpRespModel methodDispatch(String method, String body) {
		// TODO Auto-generated method stub
		HistoryMethod requestMethod = HistoryMethod.fromString(method);
		HttpRespModel respModel = new HttpRespModel();
		
		switch (requestMethod) {
		case RecordListMethod:{
			HistoryRequestUtil util = new HistoryRequestUtil();
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			respModel = util.getDailySituations(param);
			break;
		}
		case StatisticsMethod:{
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			StatisticsRequestUtil util = new StatisticsRequestUtil();
			respModel = util.getTotalStatistics(param);
			break;
		}
		case MealStatisticsMethod:{
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			StatisticsRequestUtil util = new StatisticsRequestUtil();
			respModel = util.mealStatisticsInMonths(param);
			break;
		}
		case SleepStatisticsMethod:{
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			StatisticsRequestUtil util = new StatisticsRequestUtil();
			respModel = util.sleepStatisticsInMonths(param);
			break;
		}
		case InterestStatisticsMethod:{
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			StatisticsRequestUtil util = new StatisticsRequestUtil();
			respModel = util.interestStatisticsInMonth(param);
			break; 
		}
		case RecordTestMethod:{
			HistoryRequestParam param = null;
			if (body != null && !body.isEmpty()) {
				param = (HistoryRequestParam) new HistoryRequestParam().fromJson(body);
			}
			StatisticsRequestUtil util = new StatisticsRequestUtil();
			respModel = util.testMethod(param);
			break;
		}
		default:
			respModel.setCode(RespError.urlMethodError);
			respModel.setMessage("对不起, method: " + method + "没有找到。");
			break;
		}
		return respModel;
	}

}
