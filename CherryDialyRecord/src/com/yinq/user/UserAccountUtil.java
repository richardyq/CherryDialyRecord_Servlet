package com.yinq.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.user.entity.KidInfoModel;
import com.yinq.user.entity.RegisterParam;
import com.yinq.user.entity.UserAccount;
import com.yinq.user.entity.UserModel;

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
		session.close();
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
	
	public HttpRespModel validLoginAccount(String account) {
		HttpRespModel respModel = new HttpRespModel();
		if (account == null || account.isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		UserAccount userAccount = getUserAccoun(account);
		if (userAccount != null) {
			//login account not existed
			respModel.setCode(RespError.userHasBeenExisted);
			respModel.setMessage("对不起，账号已经存在。");
			return respModel;
		}
		return respModel;
	}
	
	public HttpRespModel registerUser(RegisterParam registerParam) {
		HttpRespModel respModel = new HttpRespModel();
		if (registerParam == null || registerParam.getAccount() == null || registerParam.getAccount().isEmpty() ||
				registerParam.getPassword() == null || registerParam.getPassword().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		UserAccount userAccount = new UserAccount();
		userAccount.setAccount(registerParam.getAccount());
		userAccount.setPassword(registerParam.getPassword());
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		userAccount.setUserId(uuid);
		
		KidInfoModel kidInfoModel = new KidInfoModel();
		kidInfoModel.setName(registerParam.getKidName());
		kidInfoModel.setNickname(registerParam.getKidNickName());
		
		UserModel userModel = new UserModel();
		userModel.setUserId(uuid);
		userModel.setUserName(registerParam.getUserName());
		userModel.setRelation(registerParam.getRelation());
		userModel.setMobile(registerParam.getMobile());
		userModel.setGender(registerParam.getGender());
		userModel.setAccount(registerParam.getAccount());
		
		Session session = HibernateUtil.getSession();
		try {
			Transaction transaction = session.getTransaction();
			transaction.begin();
			session.save(kidInfoModel);
			
			
			String hql = "select max(id) from " + kidInfoModel.getClass().getName();
			Query<Integer> query = session.createQuery(hql);
			int kidId = query.uniqueResult().intValue();
			System.out.println("kid is " + kidId);
			
			kidInfoModel = session.get(KidInfoModel.class, kidId); 
			userModel.setKidId(kidInfoModel.getId());
			
			//user account
			session.save(userAccount);
			
			session.save(userModel);
			
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			respModel.setCode(RespError.databaseError);
			respModel.setMessage("数据库操作失败。");
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
		}
		
		
		session.close();
		return respModel;
	}
}
