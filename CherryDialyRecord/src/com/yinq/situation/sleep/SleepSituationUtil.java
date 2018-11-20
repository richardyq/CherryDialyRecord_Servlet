package com.yinq.situation.sleep;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SleepSituationModel;

public class SleepSituationUtil {

	public SleepSituationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	protected int addSleepSituation(SleepSituationModel aSituationModel) {
		if (aSituationModel == null) {
			return RespError.urlInvalidParamError;
		}
		
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		
		//获取已有的Situation
		try {
			
			String sql = "Select e from " + SleepSituationModel.class.getName() + " e " + " where e.date=:date and e.code=:code";
			
			Query<MealSituationModel> query = session.createQuery(sql);
			query.setParameter("code", aSituationModel.getCode());
			query.setParameter("date", aSituationModel.getDate());
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
		
		//保存操作
		try {
			 
			session.persist(aSituationModel);//persisting the object  

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
	
	protected List<SleepSituationModel> getTodaySleepSituations(String dateStr) {
		Session session = HibernateUtil.getSession();
//		SleepSituationModel transaction=session.beginTransaction(); 
		List<SleepSituationModel> situationModels = null;
		try {
			String sql = "Select e from " + SleepSituationModel.class.getName() + " e " + " where e.date=:date ";
			Query<SleepSituationModel> query = session.createQuery(sql);
			query.setParameter("date", dateStr);
			situationModels = query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
			session.getTransaction().rollback();
	        session.close();
		}
		session.close();
		
		return situationModels;
	}
	
	
	public HttpRespModel appendSleepSituation(SleepSituationModel aModel) {
		HttpRespModel respModel = new HttpRespModel();
		if (aModel == null) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		if (aModel.getDate() == null || aModel.getDate().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的日期参数不正确。");
			return respModel;
		}
		
		SleepSituationModel model = new SleepSituationModel(aModel.getDate(), aModel.getCode(), aModel.getStatus());
		int retCode = addSleepSituation(model);
		
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
			respModel.setResult(model);
		}
		return respModel;
	}
	
	public HttpRespModel getSleepSituationList(SleepSituationModel aModel) {
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
		
		List<SleepSituationModel> situationModels = getTodaySleepSituations(dateStr);
		
		respModel.setResult(situationModels);
		
		return respModel;
	}
}
