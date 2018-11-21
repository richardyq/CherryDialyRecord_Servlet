package com.yinq.situation.sleep;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.servlet.HttpRespModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SleepSituationModel;
import com.yinq.situation.entity.SleepSituationParam;

public class SleepSituationUtil {

	public SleepSituationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	protected int addSleepSituation(SleepSituationModel model) {
		if (model == null) {
			return RespError.urlInvalidParamError;
		}
		
		Session session = HibernateUtil.getSession();
		Transaction transaction=session.beginTransaction(); 
		
		//获取已有的Situation
		try {
			
			String sql = "Select e from " + SleepSituationModel.class.getName() + " e " + " where e.date=:date and e.code=:code";
			
			Query<MealSituationModel> query = session.createQuery(sql);
			query.setParameter("code", model.getCode());
			query.setParameter("date", model.getDate());
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
			 
			session.save(model);//persisting the object  

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
		ArrayList<SleepSituationModel> retModels = null;
		
		try {
			String sql = "Select e from " + SleepSituationModel.class.getName() + " e " + " where e.date=:date ";
			Query<SleepSituationModel> query = session.createQuery(sql);
			query.setParameter("date", dateStr);
			List<SleepSituationModel> situationModels = query.getResultList();
			retModels = new ArrayList<SleepSituationModel>();
			for (SleepSituationModel sleepSituationModel : situationModels) {
				retModels.add(new SleepSituationModel(sleepSituationModel));
			}
		} catch (Exception e) {
			// TODO: handle exception
			session.getTransaction().rollback();
	        session.close();
		}
		session.close();
		
		return retModels;
	}
	
	
	public HttpRespModel appendSleepSituation(SleepSituationParam param) {
		HttpRespModel respModel = new HttpRespModel();
		if (param == null) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的参数不正确。");
			return respModel;
		}
		
		if (param.getDate() == null || param.getDate().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的日期参数不正确。");
			return respModel;
		}
		
		if (param.getUserId() == null || param.getUserId().isEmpty()) {
			respModel.setCode(RespError.urlInvalidParamError);
			respModel.setMessage("对不起，您输入的用户参数不正确。");
			return respModel;
		}
		
		SleepSituationModel model = new SleepSituationModel(param);
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
	
	public HttpRespModel getSleepSituationList(SleepSituationParam aModel) {
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
