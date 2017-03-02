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

/**
 * Servlet implementation class GetFriendsList
 */
@WebServlet("/GetFriendsList")
public class GetFriendsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFriendsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ȡ����Ĳ����û���
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
	    AppUserInfo appUserInfo = new AppUserInfo();
		String info="";
		//��ʼ��������
	    Service service = new Service();
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean ok = false;
		try {
			ok = service.getFriendsList(username, appUserInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ok) {
			System.out.println("Success in Getting Friends List");
			info = appUserInfo.getFriendsList();
			//System.out.println(info);
			out.print(info);
		} else {
			System.out.println("Fail in Getting Friends List");
			out.print("failed");
		}
		out.flush();
		out.close();
	}
}
