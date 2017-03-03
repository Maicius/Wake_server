package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UserDao.AppUserInfo;
import com.checkInformation.CheckInformation;
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
		try{
			userName = URLDecoder.decode(userName, "UTF-8");
		    friendName = URLDecoder.decode(friendName, "UTF-8");
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		//��ʼ��������
	    Service service = new Service();
	    
	    response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		boolean ok3 = false;
		try {
			ok3 = CheckInformation.whetherUser(friendName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!ok3) {            //�����ж���ӵĺ����Ƿ�����ע����û�
			System.out.println("The user not register.");
			out.print("notuser");
		} else {               //�ж��Ƿ��Ѿ��Ǻ��ѹ�ϵ
			boolean ok2 = CheckInformation.whetherFriends(userName, friendName);
			if (ok2) {             //�Ѿ��Ǻ���
				System.out.println("They are already friends.");
				out.print("already");
			} else {               //�����Ǻ���
				boolean ok = false;
				try {
					ok = service.addFriend(userName, friendName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (ok)        //�ж��Ƿ���ӳɹ�
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
