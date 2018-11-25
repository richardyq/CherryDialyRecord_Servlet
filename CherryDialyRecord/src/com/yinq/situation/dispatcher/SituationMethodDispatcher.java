package com.yinq.situation.dispatcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.sql.Insert;

import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.servlet.MethodDispatcher;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.InterestSituationParam;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.MealSituationParam;
import com.yinq.situation.entity.SituationDetModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SleepSituationModel;
import com.yinq.situation.entity.SleepSituationParam;
import com.yinq.situation.interest.InterestUtil;

import com.yinq.situation.util.SituationUtil;

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
			respModel = addMealSituationHandler(body);
			break;
		}
		case TodayMealSituationMethod:{
			//获取当天吃饭记录
			respModel = getTodayMealSituations();
			break;
		}
		case AddSleepSituationMethod:{
			//添加睡觉情况记录
			respModel = addSleepSituation(body);
			break;
			
		}
		case TodaySleepSituationMethod:{
			//获取当天睡觉情况记录
			respModel = getTodaySleepSituations();
			break;
		}
		case InterestCateList:{
			//获取兴趣分类列表
			InterestUtil util = new InterestUtil();
			respModel = util.getAllInterestList();
			break;
		}
		case TodayInterestSituationMethod:{
			respModel = getTodayInterestSituations();
			break;
		}
		case AddInterestSituationMethod:{
			//添加兴趣学习清理
			respModel = addInterestSituation(body);
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
	
	/*
	 * 添加吃饭情况
	 * */
	public HttpRespModel addMealSituationHandler(String body) {
		HttpRespModel respModel = new HttpRespModel();
		
		SituationUtil util = new SituationUtil();
		MealSituationParam param = (MealSituationParam)new MealSituationParam().fromJson(body);
		SituationModel model = util.createMealSituationModel(param);
		if (model.hasBeenExisted()) {
			respModel.setCode(RespError.situationHasBeenExisted);
			respModel.setMessage("对不起，数据已经存在");
			return respModel;
		}
		
		int saveRet = 0;
		try {
			saveRet = model.save();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			saveRet = RespError.databaseError;
		}
		
		respModel.setCode(saveRet);
		
		if (saveRet != 0) {
			switch (saveRet) {
			case RespError.urlInvalidParamError:
			{
				respModel.setMessage("对不起，您输入的参数错误。");
				break;
			}
			case RespError.databaseError:
			{
				respModel.setMessage("对不起，数据库操作错误。");
				break;
			}
			default:
				break;
			}
		}
		else {
			respModel.setResult(model);
		}
		return respModel;
	}
	
	/*
	 * getTodayMealSituations
	 * 获取今天的吃饭情况
	 */
	public HttpRespModel getTodayMealSituations() {
		HttpRespModel respModel = new HttpRespModel();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(new Date());
		SituationUtil util = new SituationUtil();
		ArrayList<SituationModel> models = util.getMealSituationsFromDate(dateStr);
		if (models == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
		}
		else {
			respModel.setResult(models);
		}
		
		return respModel;
	}
	
	/*
	 * addSleepSituation
	 * 添加睡觉情况
	 */
	public HttpRespModel addSleepSituation(String body) {
		HttpRespModel respModel = new HttpRespModel();
		SituationUtil util = new SituationUtil();
		SleepSituationParam param = (SleepSituationParam)new SleepSituationParam().fromJson(body);
		SituationModel model = util.createSleepSituationModel(param);
		if (model == null) {
			respModel.setMessage("对不起，数据库操作错误。");
			return respModel;
		}
		if (model.hasBeenExisted()) {
			respModel.setCode(RespError.situationHasBeenExisted);
			respModel.setMessage("对不起，数据已经存在");
			return respModel;
		}
		
		int saveRet = 0;
		try {
			saveRet = model.save();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			saveRet = RespError.databaseError;
		}
		
		respModel.setCode(saveRet);
		
		if (saveRet != 0) {
			switch (saveRet) {
			case RespError.urlInvalidParamError:
			{
				respModel.setMessage("对不起，您输入的参数错误。");
				break;
			}
			case RespError.databaseError:
			{
				respModel.setMessage("对不起，数据库操作错误。");
				break;
			}
			default:
				break;
			}
		}
		else {
			respModel.setResult(model);
		}
		return respModel;
	}
	
	/*
	 * getTodaySleepSituations
	 * 获取当天的睡觉情况
	 */
	public HttpRespModel getTodaySleepSituations() {
		HttpRespModel respModel = new HttpRespModel();
		SituationUtil util = new SituationUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(new Date());
		
		ArrayList<SituationModel> models = util.getSleepSituationsFromDate(dateStr);
		if (models == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
		}
		else {
			respModel.setResult(models);
		}
		return respModel;
	}
	
	public HttpRespModel addInterestSituation(String body) {
		HttpRespModel respModel = new HttpRespModel();
		SituationUtil util = new SituationUtil();
		
		InterestSituationParam param = (InterestSituationParam)new InterestSituationParam().fromJson(body);
		SituationModel model = util.createInterestSituationModel(param);
		if (model == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		if (model.hasBeenExisted()) {
			respModel.setCode(RespError.situationHasBeenExisted);
			respModel.setMessage("对不起，数据已经存在");
			return respModel;
		}
		
		int saveRet = 0;
		try {
			saveRet = model.save();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			saveRet = RespError.databaseError;
		}
		
		respModel.setCode(saveRet);
		
		if (saveRet != 0) {
			switch (saveRet) {
			case RespError.urlInvalidParamError:
			{
				respModel.setMessage("对不起，您输入的参数错误。");
				break;
			}
			case RespError.databaseError:
			{
				respModel.setMessage("对不起，数据库操作错误。");
				break;
			}
			default:
				break;
			}
		}
		else {
			respModel.setResult(model);
		}
		
		return respModel;
	}
	
	/*
	 * getTodayInterestSituations
	 * 获取当天所有兴趣学习情况
	 */
	public HttpRespModel getTodayInterestSituations() {
		HttpRespModel respModel = new HttpRespModel();
		SituationUtil util = new SituationUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(new Date());
		
		ArrayList<SituationModel> models = util.getInterestSituationFromDate(dateStr);
		if (models == null) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
		}
		else {
			respModel.setResult(models);
		}
		return respModel;
	}

}

