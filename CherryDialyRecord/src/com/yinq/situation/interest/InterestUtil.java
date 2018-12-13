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
	
	public ArrayList<InterestCateModel> allInterestList(){
		
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		List<InterestCateModel> cateModels = null;
		ArrayList<InterestCateModel> retModels = null;
		try {
			String sql = "Select e from " + InterestCateModel.class.getName() + " e ";
			// Create Query object.
	        Query<InterestCateModel> query = session.createQuery(sql);


	        // Execute query.
	        cateModels = query.getResultList();
	        retModels = new ArrayList<InterestCateModel>();
	        for (InterestCateModel cateModel : cateModels) {
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
	
	
}
