package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.DAO.*;
import com.UserDao.AppUserInfo;
import com.checkInformation.CheckInformation;

public class Service {
	//ClockDataDBManager sql = new ClockDataDBManager();
	public Boolean login(String username, String password, AppUserInfo appUserInfo) throws SQLException {
		String nickname = "";
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		ResultSet rs = clockDataDBManager.executeLogQuery(username, password);
		if (rs.next()) {
			nickname = rs.getString("nickname");
			appUserInfo.setUserInfo("success" + "#" + nickname);
			ClockDBHelper.closeDB();
			return true;
		}
		ClockDBHelper.closeDB();
		return false;
	}

	public Boolean registerUser(String username, String password, String nickname, String brief_intro) throws SQLException {	
		if (CheckInformation.checkNameExist(username)){
			return false;
		}
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);		
		int ret = clockDataDBManager.executeRegistUser(username, password, nickname, brief_intro);
		if (ret != 0) {
			ClockDBHelper.closeDB();
			return true;
		}
		ClockDBHelper.closeDB();
		return false;
	}
	
	public Boolean registerTime(String username, Timestamp date) throws SQLException {
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		int ret = clockDataDBManager.executeRegistGetUpTime(username, date);
		if (ret != 0) {
			ClockDBHelper.closeDB();
			return true;
		}
		ClockDBHelper.closeDB();
		return false;
	}
	
	public Boolean getTimeHistory(String username, AppUserInfo appUserInfo) {
		
        String timeList="";
		try {
			Connection conn = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
			ResultSet rs = clockDataDBManager.executeNameQuery("up_time","getuptime", "username");
			while (rs.next()) {
				String tmp=rs.getString("up_time").substring(0, 19)+"#" ;
				timeList+=tmp;
			}
			appUserInfo.setTimeList(timeList);
			ClockDBHelper.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClockDBHelper.closeDB();
		return false;
	}
	
	public Boolean getUserInfo(String username, AppUserInfo appUserInfo) throws SQLException {

		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		try {

			ResultSet rs = clockDataDBManager.executeNameQuery("*", "appuser", username);
			if (rs.next()) {
				String user_name = rs.getString("username");
				String nickname = rs.getString("nickname");
				String brief_intro = rs.getString("brief_intro");
				appUserInfo.setUserinfo(user_name + "#" + nickname + "#" + brief_intro);
			}
			ClockDBHelper.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClockDBHelper.closeDB();
		return false;
	}

	public Boolean getFriendsList(String username, AppUserInfo appUserInfo) throws SQLException {
		String friendsList = "";
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		ResultSet rs = clockDataDBManager.executeFriendsListQuery(username);
		try {
			while (rs.next()) {
				String userName = rs.getString("username");
				String nickName = rs.getString("nickname");
				String signature = rs.getString("brief_intro");
				friendsList += userName + "#" + nickName + "#" + signature + "#";
			}
			appUserInfo.setFriendsList(friendsList);
			ClockDBHelper.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClockDBHelper.closeDB();
		return false;
	}

	public Boolean deleteFriend(String userName, String friendName) throws SQLException {
		
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		int ret = clockDataDBManager.executeDeleteFriend(userName, friendName);
		if (ret != 0) {
			ClockDBHelper.closeDB();
			return true;
		}
		ClockDBHelper.closeDB();
		return false;
	}

public Boolean searchFriend(String nickName, String userName, AppUserInfo appUserInfo) {
	String query = "";
	String friendsList ="";
	ResultSet rs = null;
	Connection conn = ClockDBHelper.createInstance();
	ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
	if (nickName.equals("") && userName.equals(""))//ͬʱΪ��
		return false;
	else if (nickName.equals("")) {                //�ǳ�Ϊ�գ����ǵ绰��Ϊ��
		rs = clockDataDBManager.executeSearchFriends(userName, userName);
		//query = "select username, nickname from appuser where username = '" + userName + "';";
	} else if (userName.equals("")) {              //ֻ���ǳ�
		
		query = "select username, nickname from appuser where nickname = '" + nickName + "';";
	} else {                                       //�ǳơ��û�������
		query = "select username, nickname from appuser where username = '"+ userName +"'"
				+ " and nickname='" + nickName + "';";
	}
	sql.connectDB();
	
	ResultSet rs = sql.executeQuery(query);
	try {
		
		while (rs.next()) {
			String uName = rs.getString("username");
			String nName = rs.getString("nickname");
			friendsList += uName + "#" + nName + "#";
		}
		appUserInfo.setFriendsList(friendsList);
		sql.closeDB();
		return true;
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	sql.closeDB();
	return false;
}





public Boolean addFriend(String userName, String friendName) {
	String query = "";
	System.out.println(userName +" " + friendName);
	if (userName.equals("") || friendName.equals("")) {  //������Ϊ�յ����
		return false;
	} else {
		query = "insert into friend_list(user_id, friends) values('"+userName+"', '"+friendName+"');";
		sql.connectDB();
		int ret = sql.executeUpdate(query);
		System.out.println(ret);
		if (ret == 0) {
			sql.closeDB();
			return false;
		}
		
		query = "insert into friend_list(user_id, friends) values('"+friendName+"', '"+userName+"');";
		int ret2 = sql.executeUpdate(query);
		System.out.println(ret2);
		if (ret2 != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();
	}
	return false;
}

public Boolean setGetUpTip(String username,String friendname, String tip) {
	String query = "insert into greeting(send_user, receive_user, greeting_text) values('"+username+"',"
			+ " '"+friendname+"', '"+tip+"');";
	
	//DBManager sql = DBManager.createInstance();
	sql.connectDB();
	int ret = sql.executeUpdate(query);
	if (ret != 0) {
		sql.closeDB();
		return true;
	}
	sql.closeDB();
	return false;
}
	
public Boolean setUserInfo(String username, String nickname, String brief_intro) {
	
			// 获取Sql查询语句
			String SqlUpdate = "update appuser set nickname='"+nickname+"',brief_intro='"+brief_intro+"'where username ='"+username+"'";
            System.out.println(SqlUpdate);
			// 获取DB对象
			//DBManager sql = DBManager.createInstance();
			sql.connectDB();

			// 操作DB对象

			sql.executeUpdate(SqlUpdate);
		
			sql.closeDB();
			return true;
}



	public boolean registerSleepTime(String username, String hour, String date) {
		// TODO Auto-generated method stub
		String regTimeSql = "insert into sleepTime (username, sleep, day) values('"
				+ username + "','" + hour + "','"+date+"') ";
		System.out.println("regTime:"+regTimeSql);
		sql.connectDB();
		
		int ret = sql.executeUpdate(regTimeSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}

	public boolean getSleepTime(String username, AppUserInfo appUserInfo) {      
		String SqlQuery = "select sleep,day from sleepTime where username ='" + username+"'";
		sql.connectDB();
		String sleepList="";
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			while (rs.next()) {
				String tmp=rs.getString("day")+" "+rs.getString("sleep")+"#" ;
				sleepList+=tmp;
			}
			appUserInfo.setSleepList(sleepList);
			sql.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public boolean getGetUpTip(String username, AppUserInfo appUserInfo) {
		// TODO Auto-generated method stub
		String SqlQuery = "select greeting_text, nickname from greeting,appuser where send_user = username and receive_user ='" + username+"'";
		
		sql.connectDB();
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			if(rs.last()) {
				String greeting=rs.getString("greeting_text");
				String send_user = rs.getString("nickname");
				String greetingInfo = greeting+"#"+send_user;
				appUserInfo.setGreetingInfo(greetingInfo);
				System.out.println(greetingInfo);
			}
			sql.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}
}
