package com.xtone.shiro.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.xtone.shiro.model.LoginInfo;


public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(LoginServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("login doget");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("login post");
		//String date = req.getParameter("data");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		logger.info("userName:"+userName);
		logger.info("password:"+password);
		//logger.info("date:"+date);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			// 4、登录，即身份验证
			subject.login(token);
			OutputStream os = resp.getOutputStream();
		    OutputStreamWriter osw = new OutputStreamWriter(os);
		    BufferedWriter bw = new BufferedWriter(osw);
		    GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
			Gson gson = gsonBuilder.create();
			LoginInfo info = new LoginInfo();
			info.setStatus("success");
			String rsp = gson.toJson(info);
			bw.write(rsp);
			bw.flush();
			bw.close();
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			logger.debug("身份验证失败 : ["+e+"]");
			req.setAttribute("erroInfo", "用户名或是密码错误");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
