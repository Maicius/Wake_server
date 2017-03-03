package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UserDao.AppUserInfo;
import com.service.Service;

@WebServlet("/GetGetUpTip")
public class GetGetUpTip extends HttpServlet{
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收信息
		String username=request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
	    //long date=Long.parseLong(strDate);    
	    Service serv = new Service();

		// 验证处理
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 AppUserInfo appUserInfo = new AppUserInfo();
		boolean ok = false;
		try {
			ok = serv.getGetUpTip(username, appUserInfo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ok) {
			System.out.print("Succss in get wake tip");
			out.print(appUserInfo.getGreetingInfo());
			//request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed in get wake tip");
			out.print("");
		}
	    
	    
		 // 返回信息

		 
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
