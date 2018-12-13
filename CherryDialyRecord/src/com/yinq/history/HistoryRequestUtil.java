package com.yinq.history;

import java.util.ArrayList;

import com.yinq.history.model.DailySituationModel;
import com.yinq.history.model.HistoryRequestParam;
import com.yinq.history.model.HistorySituationRetModel;
import com.yinq.servlet.HttpRespModel;

public class HistoryRequestUtil {

	public HistoryRequestUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public HttpRespModel getDailySituations(HistoryRequestParam param) {
		if (param == null) {
			param = new HistoryRequestParam();
			param.setStartRow(0);
			param.setRows(7);
		}
		
		if (param.getRows() == 0) {
			param.setRows(7);
		}
		
		HistoryUtil util = new HistoryUtil();
		HttpRespModel respModel = new HttpRespModel();
		HistorySituationRetModel retModel = new HistorySituationRetModel();
		
		Long dateCount = util.recordDateCount(param.getType(), param.getKidId(), param.getStartDate(), param.getEndDate());
		retModel.setCount(dateCount.intValue());
		
		ArrayList<String> dates = util.recordDateList(param.getStartRow(), param.getRows(), param.getType(),
				param.getStartDate(), param.getEndDate(), param.getKidId());
		if (util.getErrorCode() != 0) {
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			return respModel;
		}
		
		for (String date : dates) {
			DailySituationModel model = util.dailySituationModel(date, param.getType(), param.getKidId());
			if (util.getErrorCode() != 0) {
				respModel.setCode(util.getErrorCode());
				respModel.setMessage(util.getMessage());
				return respModel;
			}
			retModel.getList().add(model);
		}
		
		
		respModel.setResult(retModel);
		return respModel;
	}
}
