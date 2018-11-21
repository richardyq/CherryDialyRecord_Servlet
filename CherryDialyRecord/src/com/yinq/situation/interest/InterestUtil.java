package com.yinq.situation.interest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.bcel.internal.generic.MULTIANEWARRAY;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.situation.entity.InterestCateModel;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.MealSituationModel;

public class InterestUtil {

	public InterestUtil() {
		// TODO Auto-generated constructor stub
	}
	
	protected ArrayList<InterestCateModel> allInterestList(){
		
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		List<InterestCateModel> cateModels = null;
		ArrayList<InterestCateModel> retModels = new ArrayList<InterestCateModel>();
		try {
			String sql = "Select e from " + InterestCateModel.class.getName() + " e ";
			// Create Query object.
	        Query<InterestCateModel> query = session.createQuery(sql);


	        // Execute query.
	        cateModels = query.getResultList();
	        
	        for (InterestCateModel cateModel : retModels) {
				retModels.add(new InterestCateModel(cateModel));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	           // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
		}
		session.close();
		return retModels;
	}
	
	protected ArrayList<InterestSituationModel> interestSituationModels(String dateStr){
		List<InterestSituationModel> models = null;
		Session session = HibernateUtil.getSession();
//		Transaction transaction=session.beginTransaction(); 
		ArrayList<InterestSituationModel> retModels = new ArrayList<InterestSituationModel>();
		try {
			String sql = "Select e from " + InterestSituationModel.class.getName() + " e " + " where e.date=:date";
			
			Query<InterestSituationModel> query = session.createQuery(sql);
			query.setParameter("date", dateStr);
			models = query.getResultList();
			
			for (InterestSituationModel interestSituationModel : models) {
				retModels.add(new InterestSituationModel(interestSituationModel));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        session.close();
		}
		
		session.close();
		return retModels;
	}
	
	protected int addInterestSituaton(InterestSituationModel model) {
		int retCode = 0;
		if (model == null) {
			retCode = RespError.urlInvalidParamError;
			return retCode;
		}
		
		if (model.getDate() == null || model.getDate().length() == 0) {
			retCode = RespError.urlInvalidParamError;
			return retCode;
		}
		
		if (model.getId() == null || model.getId().length() == 0) {
			retCode = RespError.urlInvalidParamError;
			return retCode;
		}
		
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		//查询是否已经存在
		Long count = new Long(0);
		try {
			String sql = "Select count(*) from " + InterestSituationModel.class.getName() + " e " + " where e.date=:date and e.cate.cateId=:cateId";
			
			Query<Long> query = session.createQuery(sql);
			query.setParameter("date", model.getDate());
			query.setParameter("cateId", model.getCate().getCateId());
			
			count = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        session.close();
	        return RespError.databaseError;
		}
		
		if (count.intValue() > 0) {
			return RespError.situationHasBeenExisted;
		}
		
		try {
			//session.persist(model);//persisting the object  
			session.save(model);
			transaction.commit();//transaction is committed  
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        session.close();
	        return RespError.databaseError;
		}
		session.close();
		return retCode;
	}
	
	public HttpRespModel getAllInterestList() {
		HttpRespModel respModel = new HttpRespModel();
		
		List<InterestCateModel> cateModels = allInterestList();
		if (cateModels == null) {
			respModel.setCode(RespError.databaseError);
			respModel.setMessage("对不起，数据库操作错误。");
			return respModel;
		}
		
		respModel.setResult(cateModels);
		return respModel;
	}
	
	public HttpRespModel getTodayInterestSituations() {
		HttpRespModel respModel = new HttpRespModel();
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String dateStr = ft.format(new Date());
		
		List<InterestSituationModel> situationModels = interestSituationModels(dateStr);
		ArrayList<InterestSituationModel> retModels = new ArrayList<InterestSituationModel>();
		
		for (InterestSituationModel interestSituationModel : situationModels) {
			
			retModels.add(new InterestSituationModel(interestSituationModel));
		}
		respModel.setResult(retModels);
		return respModel;
	}
	
	public HttpRespModel appendInterestSituation(InterestSituationModel model) {
		HttpRespModel respModel = new HttpRespModel();
		int retCode = addInterestSituaton(model);
		if (retCode != 0) {
			switch (retCode) {
			case RespError.urlInvalidParamError:{
				respModel.setMessage("对不起，你输入的参数错误。");
				break;
			}
			case RespError.databaseError:{
				respModel.setMessage("对不起，数据库操作错误。");
				break;
			}
			case RespError.situationHasBeenExisted:{
				respModel.setMessage("对不起，数据已经存在，不能重复添加。");
				break;
			}
			default:
				break;
			}
		}
		else {
			respModel.setResult(model);
		}
		
		return respModel;
	}
}
