package com.yinq.user.dispatcher;


import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.*;
import com.yinq.user.entity.UserAccount;
import com.yinq.user.entity.UserModel;
import com.yinq.servlet.MethodDispatcher;

public class UserMethodDispatcher implements MethodDispatcher {

	@Override
	public HttpRespModel methodDispatch(String method, String body)  {
		// TODO Auto-generated method stub
		UserMethod userMethod = UserMethod.fromString(method);
		HttpRespModel respModel = new HttpRespModel();
		
		switch (userMethod) {
		case LoginMethod:{
			UserAccount paramAccount = null;
			UserAccount newAccount = new UserAccount();
			paramAccount =  (UserAccount) newAccount.fromJson(body);
			
			System.out.println("LoginMethod -----");
			if (paramAccount == null ||
					paramAccount.getAccount() == null ||
					paramAccount.getAccount().isEmpty()) {
				respModel.setCode(RespError.urlInvalidParamError);
				respModel.setMessage("对不起,您输入的account参数不正确。");
				break;
			}
			UserAccountUtil util = new UserAccountUtil();
		
			return util.userLogin(paramAccount);
			
//			break;
		}
		case UserInfoMethod:{
			System.out.println("UserInfoMethod -----");
			UserModel paramUser = (UserModel) new UserModel().fromJson(body);
			if (paramUser == null ||
					paramUser.getUserId() == null ||
					paramUser.getUserId().isEmpty()) {
				respModel.setCode(RespError.urlInvalidParamError);
				respModel.setMessage("对不起,您输入的userId参数不正确。");
				break;
			}
			UserInfoUtil util = new UserInfoUtil();
			respModel = util.getUserInfo(paramUser);
			break;
		}
		case KidInfoMethod:{
			//获取幼儿信息
			System.out.println("KidInfoMethod -----");
			
			KidUtil util = new KidUtil();
			respModel = util.getKidInfoMethod(body);
			break;
		}
		default:
		{
			respModel.setCode(RespError.urlMethodError);
			respModel.setMessage("对不起, method: " + method + "没有找到。");
		}
			break;
		}
		return respModel;
	}

	
}
