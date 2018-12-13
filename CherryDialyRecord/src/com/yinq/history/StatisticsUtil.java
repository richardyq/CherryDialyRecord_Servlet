package com.yinq.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.yinq.datamodel.HibernateUtil;
import com.yinq.datamodel.RespError;
import com.yinq.situation.entity.InterestSituationModel;
import com.yinq.situation.entity.MealSituationModel;
import com.yinq.situation.entity.SituationModel;
import com.yinq.situation.entity.SituationRecordModel;
import com.yinq.situation.entity.SleepSituationModel;

public class StatisticsUtil {
	
	public class StatisticsMonth{
		private String startDate;
		private String endDate;
		
		public StatisticsMonth() {
			
		}
		
		public StatisticsMonth(String month) {
			SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date monthDate = null;
			try {
				monthDate = monthFormat.parse(month);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(monthDate);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			startDate = format.format(calendar.getTime());
			
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			endDate = format.format(calendar.getTime());
		}
		
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		
		
	}

	private int errorCode = 0;
	private String message = "";
	
	public StatisticsUtil(){
		
	}
	
	public Long totalCount(int type, int kidId, String startDate, String endDate) {
		Long count = null;
		setErrorCode(0);
		setMessage(null);
		
		Class sitautionClass = null;
		switch (type) {
		case 1:{
			sitautionClass = MealSituationModel.class;
			break;
		}
		case 2:{
			sitautionClass = SleepSituationModel.class;
			break;
		}
		case 3:{
			sitautionClass = InterestSituationModel.class;
			break;
		}
		default:{
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的type参数错误。");
		
			return count;
			}
		}
		
		String sql = "select count(s) from " + sitautionClass.getName() + " s , " + SituationRecordModel.class.getName()
				+ " r where r.kidId=:kidId and r.type=:type and s.id=r.id";
		if (startDate != null && !startDate.isEmpty()) {
			sql += " and r.date>=:startDate";
		}
		if (endDate != null && !endDate.isEmpty()) {
			sql += " and r.date<=:endDate";
		}
		
		Session session = HibernateUtil.getSession();
		Query<Long> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("type", type);
		if (startDate != null && !startDate.isEmpty()) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && !endDate.isEmpty()) {
			query.setParameter("endDate", endDate);
		}
		
		try {
			count = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return count;
	}
	
	public Double averageScore(int type, int kidId, String startDate, String endDate) {
		Double score = null;
		setErrorCode(0);
		setMessage(null);
		
		Class sitautionClass = null;
		switch (type) {
		case 1:{
			sitautionClass = MealSituationModel.class;
			break;
		}
		case 2:{
			sitautionClass = SleepSituationModel.class;
			break;
		}
		case 3:{
			sitautionClass = InterestSituationModel.class;
			break;
		}
		default:{
			setErrorCode(RespError.urlInvalidParamError);
			setMessage("对不起，你输入的type参数错误。");
		
			return score;
			}
		}
		
		String sql = "select avg(s.score) from " + sitautionClass.getName() + " s , " + SituationRecordModel.class.getName()
				+ " r where r.kidId=:kidId and r.type=:type and s.id=r.id";
		if (startDate != null && !startDate.isEmpty()) {
			sql += " and r.date>=:startDate";
		}
		if (endDate != null && !endDate.isEmpty()) {
			sql += " and r.date<=:endDate";
		}
		
		Session session = HibernateUtil.getSession();
		Query<Double> query = session.createQuery(sql);
		query.setParameter("kidId", kidId);
		query.setParameter("type", type);
		if (startDate != null && !startDate.isEmpty()) {
			query.setParameter("startDate", startDate);
		}
		if (endDate != null && !endDate.isEmpty()) {
			query.setParameter("endDate", endDate);
		}
		
		try {
			score = query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		session.close();
		return score;
	}
	
	//获取第一条数据的日期
	public String firstRecordDate(int kidId, int type) {
		String firstDate = null;
		setErrorCode(0);
		setMessage(null);
		
		String sql = "select r.date from " + SituationRecordModel.class.getName() 
				+ " r where kidId=:kidId and type=:type " 
				+ "order by date desc";
		Session session = HibernateUtil.getSession();
		Query<String> query = session.createQuery(sql);
		query.setParameter("type", type);
		query.setParameter("kidId", kidId);
		query.setFirstResult(0);
		query.setMaxResults(1);
			
		try {
			List<String> dates = query.getResultList();
			if (dates == null || dates.size() == 0) {
				setErrorCode(RespError.dataNotFound);
				setMessage("对不起，没有相应数据。");
			}
			else {
				firstDate = dates.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		return firstDate;
	}
	
	//获取最后一条数据的日期
	public String lastRecordDate(int kidId, int type) {
		String lastDate = null;
		setErrorCode(0);
		setMessage(null);
		
		String sql = "select r.date from " + SituationRecordModel.class.getName() 
				+ " r where kidId=:kidId and type=:type "
				+ "order by date";
		Session session = HibernateUtil.getSession();
		Query<String> query = session.createQuery(sql);
		query.setParameter("type", type);
		query.setParameter("kidId", kidId);
		query.setFirstResult(0);
		query.setMaxResults(1);
			
		try {
			List<String> dates = query.getResultList();
			if (dates == null || dates.size() == 0) {
				setErrorCode(RespError.dataNotFound);
				setMessage("对不起，没有相应数据。");
			}
			else {
				lastDate = dates.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			setErrorCode(RespError.databaseError);
			setMessage("对不起，数据库操作失败。");
		}
		return lastDate;
	}
	
	public ArrayList<String> getRecordMonths(int kidId, int type, String startDate, String endDate){
		ArrayList<String> months = new ArrayList<String>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(format.parse(startDate));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int startYear = cal.get(Calendar.YEAR);
		int startMonth = cal.get(Calendar.MONTH);
		System.out.println("start " + startDate);
		System.out.println("endDate " + endDate);
		
		System.out.println("start year: " + startYear + " end month: " + startMonth);
		
		SimpleDateFormat monthformat = new SimpleDateFormat("yyyy-MM");
		try {
			cal.setTime(format.parse(endDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		while (true) {

			cal.set(year, month , 1);
			Date dateMonth = cal.getTime();
			
			int daysInMonth = cal.get(Calendar.DAY_OF_MONTH);
			
			String monthString = monthformat.format(dateMonth);
			
			String monthStartDate = format.format(cal.getTime());
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			//cal.set(year, month , daysInMonth);
			String monthEndDate = format.format(cal.getTime());
			cal.add(Calendar.MONTH, -1);
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH);
			System.out.println("getRecordMonths " + year + ", " + month);
			
			System.out.println("total count in month " + monthStartDate + ", " + monthEndDate);
			Long count = totalCount(type, kidId, monthStartDate, monthEndDate);
			if (count != null && count.intValue() > 0) {
				months.add(monthString);
			}
			
			if (year < startMonth) {
				break;
			}
			//break;
			if (startYear == year && month < startMonth) {
				break;
			}
		}
		
		return months;
	}
	
	public StatisticsMonth statisticsMonth(String month) {
		return new StatisticsMonth(month);
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
