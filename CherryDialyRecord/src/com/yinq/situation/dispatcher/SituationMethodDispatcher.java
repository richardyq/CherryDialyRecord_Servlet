package com.yinq.situation.dispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.sql.Insert;

import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.servlet.MethodDispatcher;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.InterestSituationParam;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SleepSituationModel;
import com.yinq.situation.interest.InterestUtil;
import com.yinq.situation.meal.MealSituationUtil;
import com.yinq.situation.sleep.SleepSituationUtil;

public class SituationMethodDispatcher implements MethodDispatcher{

	@Override
	public HttpRespModel methodDispatch(String method, String body) {
		
		// TODO Auto-generated method stub
		SituationMethod requestMethod = SituationMethod.fromString(method);
		HttpRespModel respModel = new HttpRespModel();
		
		
		switch (requestMethod) {
		case AddMealSituationMethod:
		{
			//添加吃饭记录
			MealSituationUtil util = new MealSituationUtil();
			MealSituationModel mealModel = (MealSituationModel)new MealSituationModel().fromJson(body);
			
			respModel = util.AppendMealSituation(mealModel);
			break;
		}
		case TodayMealSituationMethod:{
			//获取当天吃饭记录
			MealSituationUtil util = new MealSituationUtil();
			MealSituationModel todayModel = new MealSituationModel();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(new Date());
			todayModel.setDate(dateStr);
			respModel = util.getMealSituaionList(todayModel);
			break;
		}
		case AddSleepSituationMethod:{
			//添加睡觉情况记录
			SleepSituationModel sleepModel = (SleepSituationModel)new SleepSituationModel().fromJson(body);
			SleepSituationUtil util = new SleepSituationUtil();
			respModel = util.appendSleepSituation(sleepModel);
			break;
		}
		case TodaySleepSituationMethod:{
			//获取当天睡觉情况记录
			SleepSituationUtil util = new SleepSituationUtil();
			SleepSituationModel todayModel = new SleepSituationModel();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(new Date());
			todayModel.setDate(dateStr);
			respModel = util.getSleepSituationList(todayModel);
			break;
		}
		case InterestCateList:{
			//获取
			InterestUtil util = new InterestUtil();
			respModel = util.getAllInterestList();
			break;
		}
		case TodayInterestSituationMethod:{
			InterestUtil util = new InterestUtil();
			respModel = util.getTodayInterestSituations();
			break;
		}
		case AddInterestSituationMethod:{
			InterestUtil util = new InterestUtil();
			InterestSituationParam param = (InterestSituationParam) new InterestSituationParam().fromJson(body);
			InterestSituationModel model = new InterestSituationModel(param.getDate(), param.getCateId(), param.getStatus());
			respModel = util.appendInterestSituation(model);
			break;
		}
		case UnkonwnMethod:
		default:{
			respModel.setCode(RespError.urlMethodError);
			respModel.setMessage("对不起, method: " + method + "没有找到。");
		}
			break;
			
		}
		return respModel;
	}

}
