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
		case RecordTestMethod:{
			
			HistoryUtil util = new HistoryUtil();
			/*
//			Long count = util.totalRecordsCount();
			Long count = util.recordDateCount();
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			respModel.setResult(count);
			*/
			
			/*
			ArrayList<SituationRecordModel> recordModels = util.recordsFormDate("2018-11-23");
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			respModel.setResult(recordModels);
			*/
			
			/*
			ArrayList<String> dates = util.recordDateList(0, 7);
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			respModel.setResult(dates);
			*/
			
			DailySituationModel model = util.dailySituationModel("2018-11-23", 0, 1);
			respModel.setCode(util.getErrorCode());
			respModel.setMessage(util.getMessage());
			respModel.setResult(model);
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
