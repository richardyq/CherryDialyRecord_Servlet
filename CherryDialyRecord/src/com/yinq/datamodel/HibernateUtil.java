package com.yinq.datamodel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private HibernateUtil(){
	}
	
	// 1.创建工厂对象;
	
	private static SessionFactory sessionFactory;
	
	// 2.初始化工厂对象;
	static {
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
		
	}
	
	// 3.获得Session;
	public static Session getSession() {
		return sessionFactory.openSession();
	}
}
