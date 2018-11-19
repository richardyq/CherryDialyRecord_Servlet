package com.yinq.situation.dispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.cj.util.Util;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.servlet.MethodDispatcher;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.meal.MealSituationUtil;

public class SituationMethodDispatcher implements MethodDispatcher{

	@Override
	public HttpRespModel methodDispatch(String method, String body) {
		
		// TODO Auto-generated method stub
		SituationMethod requestMethod = SituationMethod.fromString(method);
		HttpRespModel respModel = new HttpRespModel();
		MealSituationUtil util = new MealSituationUtil();
		
		switch (requestMethod) {
		case AddMealSituationMethod:
		{
			MealSituationModel mealModel = (MealSituationModel)new MealSituationModel().fromJson(body);
			
			respModel = util.AppendMealSituation(mealModel);
			break;
		}
		case TodayMealSituationMethod:{
			MealSituationModel todayModel = new MealSituationModel();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(new Date());
			todayModel.setDate(dateStr);
			respModel = util.getMealSituaionList(todayModel);
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
