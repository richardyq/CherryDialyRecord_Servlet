package com.yinq.funny.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.funny.entity.FunnyModel;
import com.yinq.funny.entity.FunnyRequestParam;

public class FunnyUtil {

	private int errCode;
	private String message;
	
	public FunnyUtil() {
		
	}
	
	public FunnyModel addFunny(String userId, String content, String date, int kidId) {
		errCode = 0;
		message = "";
		if (userId == null || userId.isEmpty()) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的userId参数不正确。";
			return null;
		}
		
		if (date == null || date.isEmpty()) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的date参数不正确。";
			return null;
		}
		
		if (content == null || content.isEmpty()) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的content参数不正确。";
			return null;
		}
		
		if (kidId == 0 ) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的kidId参数不正确。";
			return null;
		}
		
		FunnyRequestParam param = new FunnyRequestParam(userId, content, date, kidId);
		FunnyModel funnyModel = new FunnyModel(param);
		
		Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		
		try {
			session.save(funnyModel);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			funnyModel = null;
			e.printStackTrace();
			transaction.rollback();
			session.close();
			message = "对不起，数据库操作失败。";
			errCode = RespError.databaseError;
		}
		session.close();
		return funnyModel;
	}
	
	public Long getFunnyCount(int kidId, int startRow, int rows, String startDate, String endDate) {
		errCode = 0;
		message = "";
		
		Long count = null;
		
		if (kidId == 0) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的kidId参数不正确";
			return count;
		}
		
		String sql = "select count(*) from " + FunnyModel.class.getName() + " e where kidId=:kidId ";
		String condition = "";
		
		if (startDate != null && !startDate.isEmpty()) {
			condition += " and date>=:startDate";
		}
		if (endDate != null && !endDate.isEmpty()) {
			condition += " and date<=:endDate";
		}
		
		sql += condition;
		
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		
		if (startDate != null && startDate.isEmpty() == false) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && endDate.isEmpty() == false) {
			query.setParameter("endDate", endDate);
		}
		
		try {
			count = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return count;
	}
	
	public List<FunnyModel> getFunnyList(int kidId, int startRow, int rows, String startDate, String endDate) {
		List<FunnyModel> models = null;
		errCode = 0;
		message = "";
		
		if (kidId == 0) {
			errCode = RespError.urlInvalidParamError;
			message = "对不起，你输入的kidId参数不正确";
			return models;
		}
		if (rows <= 0) {
			rows = 20;
		}
		
		String sql = "select e from " + FunnyModel.class.getName() + " e where kidId=:kidId ";
		String condition = "";
		
		if (startDate != null && !startDate.isEmpty()) {
			condition += " and date>=:startDate";
		}
		if (endDate != null && !endDate.isEmpty()) {
			condition += " and date<=:endDate";
		}
		sql += condition;
		sql += " order by updateTime desc";
		
		Session session = HibernateUtil.getSession();
		Query<FunnyModel> query = session.createQuery(sql);
		query.setFirstResult(startRow);
		query.setMaxResults(rows);
		query.setParameter("kidId", kidId);
		if (startDate != null && !startDate.isEmpty()) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && !endDate.isEmpty()) {
			query.setParameter("endDate", endDate);
		}
		try {
			List<FunnyModel> funnyModels = query.getResultList();
			ArrayList<FunnyModel> list = new ArrayList<FunnyModel>();
			for (FunnyModel funnyModel : funnyModels) {
				list.add(new FunnyModel(funnyModel));
			}
			models = list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return models;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
