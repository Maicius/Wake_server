package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class GetUpTime extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收信息
		String username=request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
	    String strDate=request.getParameter("date");
	    long lDate=Long.parseLong(strDate);
	    
	    java.sql.Timestamp date=new java.sql.Timestamp(lDate);
	    
	    Service serv = new Service();

		// 验证处理
		boolean ok = serv.registerTime(username, date);
		if (ok) {
			System.out.print("Succss in registerTime");
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed to registerTime");
		}
	    
		
	    System.out.println("  User:"+username+"  GetUpTime:"+date);
	    
		 // 返回信息
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 out.print("User:"+username+"GetUpTime:"+date);
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
