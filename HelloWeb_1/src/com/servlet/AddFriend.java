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
 * Servlet implementation class AddFriend
 */
@WebServlet("/AddFriend")
public class AddFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFriend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("userName");
		String friendName = request.getParameter("friendName");
		//��ʼ��������
	    Service service = new Service();
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean ok3 = service.whetherUser(friendName);
		if (!ok3) {            //�����ж����ӵĺ����Ƿ�����ע����û�
			System.out.println("The user not register.");
			out.print("notuser");
		} else {               //�ж��Ƿ��Ѿ��Ǻ��ѹ�ϵ
			boolean ok2 = service.whetherFriends(userName, friendName);
			if (ok2) {             //�Ѿ��Ǻ���
				System.out.println("They are already friends.");
				out.print("already");
			} else {               //�����Ǻ���
				boolean ok = service.addFriend(userName, friendName);
				if (ok)        //�ж��Ƿ����ӳɹ�
				{
					System.out.println("Success in Add Friend.");
					out.print("success");
				} else {
					System.out.println("Fail in Add Friend.");
					out.print("failed");
				}
			}
		}
		out.flush();
		out.close();
	}

}