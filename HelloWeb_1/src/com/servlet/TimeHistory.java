package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

/**
 * Servlet implementation class TimeHistory
 */
@WebServlet("/TimeHistory")
public class TimeHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TimeHistory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 接收信息
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String result="";
		System.out.println("***"+username);

		// 新建服务对象
		Service serv = new Service();

		 // 返回信息
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("text/html");
		 PrintWriter out = response.getWriter();
		 
		// 验证处理
		boolean ok = serv.getTimeHistory(username);
		if (ok) {
			result=serv.timeList;
			System.out.print("Succss in getTimeHistory");
			System.out.print(result);
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
			 out.print(result);
			 
		} else {
			 System.out.print("failed");
			 out.print("failed");
		}

		 out.flush();
		 out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
