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
 * Servlet implementation class SearchFriend
 */
@WebServlet("/SearchFriend")
public class SearchFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nickName = request.getParameter("userName");
		String userName = request.getParameter("friendName");
		String info = "";
		//初始化处理类
	    Service service = new Service();
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean ok = service.searchFriend(nickName, userName);
		if (ok) {
			System.out.println("Success in Getting Friends List");
			info = service.friendsList;
			//System.out.println(info);
			if (info.equals("")) {
				System.out.println("Search nothing!");
				out.print("nothing");
			} else {
				out.print(info);
			}
		} else {
			System.out.println("Fail in Getting Friends List");
			out.print("failed");
		}
		out.flush();
		out.close();
	}

}
