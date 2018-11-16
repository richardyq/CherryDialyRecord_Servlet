package com.yinq.user.dispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.*;
import com.yinq.user.entity.UserAccount;
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
			
			return userLogin(paramAccount);
			
//			break;
		}
		case UserInfoMethod:{
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

	public HttpRespModel userLogin(UserAccount paramAccount) {
		HttpRespModel respModel = new HttpRespModel();
		
		UserAccountUtil util = new UserAccountUtil();
		UserAccount account = util.getUserAccoun(paramAccount.getAccount());
		
		if (account == null) {
			//找好没有账号
			respModel.setCode(RespError.userAccountNotFound);
			respModel.setMessage("对不起, 没有找到指定账号。");
			return respModel;
		}
		
		if (account.getPassword().equals(paramAccount.getPassword()) == false) {
			//登录密码不正确
			respModel.setCode(RespError.userPasswordError);
			respModel.setMessage("对不起, 登录密码不正确。");
			return respModel;
		}
		
		//修改登录日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loginDateTime = simpleDateFormat.format(new Date());
		account.setLastLoginTime(loginDateTime);
		int retCode = util.updateUserAccount(account);
		if (retCode != 0) {
			respModel.setCode(RespError.databaseError);
			respModel.setMessage("对不起, 数据库操作失败。");
			return respModel;
		}
		
		respModel.setResult(account);
		return respModel;
	}
}
