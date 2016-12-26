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
 * Servlet implementation class SetGetUpTip
 */
@WebServlet("/SetGetUpTip")
public class SetGetUpTip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetGetUpTip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String friendname = request.getParameter("friendname");
		String tip = request.getParameter("tip");
	    Service service = new Service();
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean ok = service.setGetUpTip(username, friendname, tip);
		if (ok)
		{
			System.out.println("Success in Set Get-Up Tip.");
			out.print("success");
		} else {
			System.out.println("Fail in Set Get-Up Tip.");
			out.print("failed");
		}
		out.flush();
		out.close();
	}

}
