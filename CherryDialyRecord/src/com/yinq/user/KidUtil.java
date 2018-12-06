package com.yinq.user;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.JsonModel;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.entity.KidInfoModel;

public class KidUtil {
	
	public static class KidParam extends JsonModel{
		private int id;
		
		public KidParam() {
			
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
	
	int errCode;
	String message;

	public KidUtil() {
		
	}
	
	/*
	 * getKidInfo
	 * 获取幼儿信息
	 */
	public KidInfoModel getKidInfo(int id) {
		errCode = 0;
		message = null;
		KidInfoModel model = null;
		
		if (id == 0) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的id错误。";
		}
		
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.beginTransaction();
			model = session.get(KidInfoModel.class, id);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        errCode = RespError.databaseError;
			message = "对不起，数据库操作失败。";
	        session.close();
			
		}
		
		session.close();
		if (model == null) {
			errCode = RespError.dataNotFound;
			message = "对不起，没有找到对应数据。";
		}
		return model;
	}
	
	public HttpRespModel getKidInfoMethod(String paramJson) {
		HttpRespModel respModel = new HttpRespModel();
		
		KidParam param = (KidParam) new KidParam().fromJson(paramJson);
		
		KidInfoModel infoModel = getKidInfo(param.getId());
		respModel.setCode(errCode);
		respModel.setMessage(message);
		respModel.setResult(infoModel);
		
		return respModel;
	}
}
