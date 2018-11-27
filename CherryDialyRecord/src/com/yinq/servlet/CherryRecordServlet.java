package com.yinq.servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yinq.datamodel.RespError;
import com.yinq.history.HistoryMethodDispatcher;
import com.yinq.situation.dispatcher.SituationMethodDispatcher;
import com.yinq.user.dispatcher.UserMethodDispatcher;


public class CherryRecordServlet extends HttpServlet {

	private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException
	  {
	      // 执行必需的初始化
	      message = "Hello World";
	  }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 设置响应内容类型
		resp.setContentType("text/html");

	      // 实际的逻辑是在这里
	      PrintWriter out = resp.getWriter();
	      out.println("<h1>" + message + "</h1>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doPost(req, resp);
		
		//参数列表
		Enumeration<String> pNames=request.getParameterNames();
		while(pNames.hasMoreElements()){
			String paramName=(String)pNames.nextElement();
			System.out.println("postParam name: " + paramName);
		}
				
		String service = request.getParameter("service");
		String method = request.getParameter("method");
		System.out.println("get post url param service: " + service
				+ ", method: " + method);	
		
		//1.判断当前request消息实体的总长度
        int totalBytes = request.getContentLength();
      //将request的输入流读入到bytes中
        InputStream inputStream = request.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte[] bytes = new byte[totalBytes]; 
        dataInputStream.readFully(bytes); 
        dataInputStream.close();
        String body = new String(bytes);
        System.out.println("body = " + body);
        
        HttpRespModel respModel = dispatchService(service, method, body);
        String respString = respModel.toJson();
        System.out.println("resp is: " + respString);
        
        resp.setHeader("Content-type", "text/html;charset=UTF-8");  
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(respString);
	}

	public HttpRespModel dispatchService(String service, String method, String body) {
		HttpRespModel model = new HttpRespModel();
		
		HttpRequestService requestService = HttpRequestService.fromString(service);
		switch (requestService) {
		case UserService:{
			UserMethodDispatcher dispatcher = new UserMethodDispatcher();
			model = dispatcher.methodDispatch(method, body);
			break;
		}
		case SituationService:{
			SituationMethodDispatcher dispatcher = new SituationMethodDispatcher();
			model = dispatcher.methodDispatch(method, body);
			break;
		}
		case HistoryService:{
			HistoryMethodDispatcher dispatcher = new HistoryMethodDispatcher();
			model = dispatcher.methodDispatch(method, body);
			break;
		}
		default:{
			//Service 不合法
			model.setCode(RespError.urlServiceError);
			model.setMessage("对不起，Service:"+service + "没有找到。");
			break;
			}
		}
		return model;
	}
}
