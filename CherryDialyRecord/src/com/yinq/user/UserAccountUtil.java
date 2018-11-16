package com.yinq.user;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
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
}
