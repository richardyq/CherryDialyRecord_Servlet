package com.yinq.funny.util;

import java.util.List;

import com.yinq.funny.entity.FunnyListModel;
import com.yinq.funny.entity.FunnyModel;
import com.yinq.funny.entity.FunnyRequestParam;
import com.yinq.servlet.HttpRespModel;

public class FunnyRequestUtil {
	
	public FunnyRequestUtil() {
		
	}

	public HttpRespModel addFunny(String bodyJson) {
		HttpRespModel respModel = new HttpRespModel();
		FunnyUtil funnyUtil = new FunnyUtil();
		FunnyRequestParam param = (FunnyRequestParam) new FunnyRequestParam().fromJson(bodyJson);
		
		FunnyModel funnyModel = funnyUtil.addFunny(param.getUserId(), 
				param.getContent(), param.getDate(), param.getKidId());
		if (funnyModel == null) {
			respModel.setCode(funnyUtil.getErrCode());
			respModel.setMessage(funnyUtil.getMessage());
		}
		else {
			respModel.setResult(funnyModel);
		}
		
		return respModel;
	}
	
	public HttpRespModel funnyCount(String bodyJson) {
		HttpRespModel respModel = new HttpRespModel();
		FunnyUtil funnyUtil = new FunnyUtil();
		FunnyRequestParam param = (FunnyRequestParam) new FunnyRequestParam().fromJson(bodyJson);
		
		Long count = funnyUtil.getFunnyCount(param.getKidId(), param.getStartRow(),
				param.getRows(), param.getStartDate(), param.getEndDate());
		if (count == null) {
			respModel.setCode(funnyUtil.getErrCode());
			respModel.setMessage(funnyUtil.getMessage());
		}
		else {
			respModel.setResult(count);
		}
		return respModel;
	}
	
	public HttpRespModel funnyList(String bodyJson) {
		HttpRespModel respModel = new HttpRespModel();
		FunnyUtil funnyUtil = new FunnyUtil();
		FunnyRequestParam param = (FunnyRequestParam) new FunnyRequestParam().fromJson(bodyJson);
		
		FunnyListModel listModel = null;
		Long count = funnyUtil.getFunnyCount(param.getKidId(), param.getStartRow(),
				param.getRows(), param.getStartDate(), param.getEndDate());
		if (count == null) {
			respModel.setCode(funnyUtil.getErrCode());
			respModel.setMessage(funnyUtil.getMessage());
			return respModel;
		}
		else {
			
		}
		
		List<FunnyModel> list = funnyUtil.getFunnyList(param.getKidId(), param.getStartRow(),
				param.getRows(), param.getStartDate(), param.getEndDate());
		if (list == null) {
			respModel.setCode(funnyUtil.getErrCode());
			respModel.setMessage(funnyUtil.getMessage());
			return respModel;
		}
		
		listModel = new FunnyListModel();
		listModel.setCount(count.intValue());
		listModel.setList(list);
		
		respModel.setResult(listModel);
		return respModel;
		
	}
}
