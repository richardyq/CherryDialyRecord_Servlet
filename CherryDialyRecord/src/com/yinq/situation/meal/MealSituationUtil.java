package com.yinq.situation.meal;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.sun.org.apache.regexp.internal.recompile;
import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.user.entity.UserAccount;

public class MealSituationUtil {

	public MealSituationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public int addMealSituation(MealSituationModel mealModel) {
		if (mealModel == null) {
			return RespError.urlInvalidParamError;
		}
		
		//获取已有的Situation
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		//transaction.begin();
		try {
			
			String sql = "Select e from " + MealSituationModel.class.getName() + " e " + " where e.date=:date and e.mealCode=:code";
			
			Query<MealSituationModel> query = session.createQuery(sql);
			query.setParameter("code", mealModel.getMealCode());
			query.setParameter("date", mealModel.getDate());
			
			List<MealSituationModel> meals = query.getResultList();
			if (meals.size() > 0) {
				//已经存在
				session.close();
				return RespError.situationHasBeenExisted;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	        // Rollback in case of an error occurred.
	        session.getTransaction().rollback();
	        session.close();
	        return RespError.databaseError;
		}
		
		try {
			 
			session.persist(mealModel);//persisting the object  

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
		return 0;
	}
	
	protected List<MealSituationModel> getMealSituationWithDate(String dateStr) {
		Session session = HibernateUtil.getSession();
//		Transaction transaction=session.beginTransaction(); 
		List<MealSituationModel> meals = null;
		try {
			String sql = "Select e from " + MealSituationModel.class.getName() + " e " + " where e.date=:date ";
			Query<MealSituationModel> query = session.createQuery(sql);
			query.setParameter("date", dateStr);
			meals = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			session.getTransaction().rollback();
	        session.close();
		}
		session.close();
		
		return meals;
	}
	
	public HttpRespModel AppendMealSituation(MealSituationModel model) {
		HttpRespModel respModel = new HttpRespModel();
		if (model == null) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		if (model.getDate() == null || model.getDate().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的日期参数不正确。");
			return respModel;
		}
		
		MealSituationModel mealMdoel = new MealSituationModel(model.getDate(), model.getMealCode(),model.getSpeed(), 
				model.getAmount(), model.getFeed());
		
		int retCode = addMealSituation(mealMdoel);
		respModel.setCode(retCode);
		if (retCode != 0) {
			switch (retCode) {
			case RespError.urlInvalidParamError:{
				respModel.setMessage("对不起，您输入的参数不正确。");
			}
			
			break;
			
			case RespError.databaseError:{
				respModel.setMessage("对不起，数据库操作失败。");
			}
			break;
			case RespError.situationHasBeenExisted:{
				respModel.setMessage("对不起，数据已经存在。");
			}
			break;
			default:{
				respModel.setMessage("对不起，未知错误。");
			}
			break;
			}
		}
		else {
			Session session = HibernateUtil.getSession();
			try {
				session.getTransaction().begin();
				MealSituationModel retModel = session.get(MealSituationModel.class, mealMdoel.getMealId()); 
				respModel.setResult(retModel);
			}
			catch (Exception e) {
				// TODO: handle exception
				session.close();
			}
			session.close();
		}
			
		return respModel;
	}
	
	public HttpRespModel getMealSituaionList(MealSituationModel aModel) {
		HttpRespModel respModel = new HttpRespModel();
		if (aModel == null) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		String dateStr = aModel.getDate();
		if (dateStr == null || dateStr.isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数:date不正确。");
			return respModel;
		}
		
		List<MealSituationModel> mealSituationModels = getMealSituationWithDate(dateStr);
		respModel.setResult(mealSituationModels);
		
		return respModel;
	}
		
}
