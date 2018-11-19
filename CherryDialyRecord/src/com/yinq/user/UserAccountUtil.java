package com.yinq.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.entity.UserAccount;

public class UserAccountUtil {

	public UserAccount getUserAccoun(String account) {
		UserAccount userAccount = null;
		Session session = HibernateUtil.getSession();
		try {
			session.getTransaction().begin();
			userAccount = session.get(UserAccount.class, account); 
			/*
			String sql = "Select e from " + UserAccount.class.getName() + " e " + " where e.account=:account ";
		
			Query<UserAccount> query = session.createQuery(sql);
			query.setParameter("account", account);
			List<UserAccount> accounts = query.getResultList();
			*/
			if (userAccount != null) {
				
				System.out.println("UserAccount::getUserAccoun account: " + account);
				System.out.println("ret: " + userAccount.toJson());
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        session.close();
		}
		
		session.close();
		return userAccount;
	}
	
	public int updateUserAccount(UserAccount userAccount) {
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.update(userAccount);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
		}
		
		return 0;
	}
	
	public HttpRespModel userLogin(UserAccount aUserAccount) {
		HttpRespModel respModel = new HttpRespModel();
		
		//check input param
		if (aUserAccount == null || aUserAccount.getAccount() == null || aUserAccount.getAccount().isEmpty() ||
				aUserAccount.getPassword() == null || aUserAccount.getPassword().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		UserAccount userAccount = getUserAccoun(aUserAccount.getAccount());
		if (userAccount == null) {
			//login account not existed
			respModel.setCode(RespError.userAccountNotFound);
			respModel.setMessage("对不起，没有找到指定的账号。");
			return respModel;
		}
		//check password
		if (userAccount.getPassword().equals(aUserAccount.getPassword()) == false) {
			//登录密码不正确
			respModel.setCode(RespError.userPasswordError);
			respModel.setMessage("对不起, 登录密码不正确。");
			return respModel;
		}
		
		//修改登录日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loginDateTime = simpleDateFormat.format(new Date());
		userAccount.setLastLoginTime(loginDateTime);
		int retCode = updateUserAccount(userAccount);
		if (retCode != 0) {
			respModel.setCode(RespError.databaseError);
			respModel.setMessage("对不起, 数据库操作失败。");
			return respModel;
		}
		
		respModel.setResult(userAccount);
		return respModel;
	}
}
