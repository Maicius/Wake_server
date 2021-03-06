package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UserDao.AppUserInfo;
import com.service.Service;

public class LogLet extends HttpServlet {

	private static final long serialVersionUID = 369840050351775312L;

	/**
	 * The doGet method of the Server let.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 接收信息
		String username = request.getParameter("username");
		System.out.println("username="+username);
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String password = request.getParameter("password");
		Service serv = new Service();
        AppUserInfo appUserInfo = new AppUserInfo();
		 // 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		boolean loged =false;
		try {
			loged = serv.login(username, password, appUserInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (loged) {
			 System.out.print("Succss in Login");
			 out.print(appUserInfo.getUserInfo());	
		} else {
			 out.print("failed");
		}

		 out.flush();
		 out.close();

	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
