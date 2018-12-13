package com.yinq.funny.dispatcher;

import com.yinq.funny.util.FunnyRequestUtil;
import com.yinq.servlet.HttpRespModel;
import com.yinq.servlet.MethodDispatcher;

public class FunnyMethodDispatcher implements MethodDispatcher {

	@Override
	public HttpRespModel methodDispatch(String method, String body) {
		// TODO Auto-generated method stub
		HttpRespModel respModel = new HttpRespModel();
		FunnyMethod funnyMethod = FunnyMethod.fromString(method);
		
		switch (funnyMethod) {
		case AddFunnyMethod:{
			//添加趣事
			FunnyRequestUtil util = new FunnyRequestUtil();
			respModel = util.addFunny(body);
			break;
		}
		case FunnyListMethod:{
			FunnyRequestUtil util = new FunnyRequestUtil();
			respModel = util.funnyList(body);
			break;
		}
		default:
			break;
		}
		return respModel;
	}

}
