package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;
@WebServlet("/SleepTime")
public class SleepTime extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收信息
		String username=request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
	    String date=request.getParameter("date");
	    //long date=Long.parseLong(strDate);    
	    Service serv = new Service();

		// 验证处理
		boolean ok = serv.registerSleepTime(username, date);
		if (ok) {
			System.out.print("Succss in update SleepTime");
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed in update SleepTime");
		}
	    
		
	    System.out.println("  User:"+username+"  SleepTime:"+date);
	    
		 // 返回信息
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.print("User:"+username+"SleepTime:"+date);
		 out.flush();
		 out.close();

	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
