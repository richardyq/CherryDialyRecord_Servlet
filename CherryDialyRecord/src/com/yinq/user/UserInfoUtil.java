package com.yinq.user;

import org.hibernate.Session;


import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.entity.UserModel;

public class UserInfoUtil {

	public UserInfoUtil() {
		// TODO Auto-generated constructor stub
	}
	
	private UserModel getUserModel(String userId) {
		Session session = HibernateUtil.getSession();
		UserModel userInfo = null;
		
		try {
			session.getTransaction().begin();
			userInfo = session.get(UserModel.class, userId); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
		}
		
		return userInfo;
	}
	
	public HttpRespModel getUserInfo(UserModel aModel) {
		HttpRespModel respModel = new HttpRespModel();
		
		//check input param
		if (aModel == null|| aModel.getUserId() == null || aModel.getUserId().isEmpty() == true) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		UserModel userModel = getUserModel(aModel.getUserId());
		if (userModel == null) {
			respModel.setCode(RespError.userNotFound);
			respModel.setMessage("对不起，没有找到指定用户信息。");
			return respModel;
		}
		
		respModel.setResult(userModel);
		return respModel;
	}
}
