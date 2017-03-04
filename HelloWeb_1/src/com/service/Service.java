package com.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.DAO.*;
import com.UserDao.AppUserInfo;
import com.checkInformation.CheckInformation;

public class Service {

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
			String column = "up_time";
			String table = "getuptime";
			System.out.println(username);
			ResultSet rs = clockDataDBManager.executeNameQuery(column, table, username);
			
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
	
	public Boolean getUserInfo(String username, AppUserInfo appUserInfo) {
		try {
			Connection conn = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);

			String column = "*";
			String table = "appuser";
			ResultSet rs = clockDataDBManager.executeNameQuery(column, table, username);
			if (rs.next()) {
				String user_name = rs.getString("username");
				String nickname = rs.getString("nickname");
				String brief_intro = rs.getString("brief_intro");
				appUserInfo.setUserinfo(user_name + "#" + nickname + "#" + brief_intro);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ClockDBHelper.closeDB();
		}

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

	public Boolean searchFriend(String nickName, String userName, AppUserInfo appUserInfo) throws SQLException {
		String friendsList = "";
		ResultSet rs = null;
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		if (nickName.equals("") && userName.equals(""))// ͬʱΪ��
			return false;
		else if (userName.equals("")) { // �ǳ�Ϊ�գ����ǵ绰��Ϊ��
			String column = "username";
			rs = clockDataDBManager.executeSearchFriends(column, userName);
		} else if (nickName.equals("")) { // ֻ���ǳ�
			String column = "nickname";
			rs = clockDataDBManager.executeSearchFriends(column, nickName);
		} else { // �ǳơ��û�������
			String column = "username";
			rs = clockDataDBManager.executeSearchFriends(column, userName);
		}
		try {
			while (rs.next()) {
				String uName = rs.getString("username");
				String nName = rs.getString("nickname");
				friendsList += uName + "#" + nName + "#";
			}
			appUserInfo.setFriendsList(friendsList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean addFriend(String userName, String friendName) throws SQLException {
		System.out.println(userName + " " + friendName);
		if (userName.equals("") || friendName.equals("")) { // ������Ϊ�յ����
			return false;
		} else {
			// 为用户A添加好友B
			Connection conn = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
			int ret = clockDataDBManager.executeAddFriend(userName, friendName);
			System.out.println(ret);
			if (ret == 0) {
				ClockDBHelper.closeDB();
				return false;
			}
			// 为用户B添加好友A，即双向添加
			ret = clockDataDBManager.executeAddFriend(friendName, userName);
			System.out.println(ret);
			if (ret != 0) {
				ClockDBHelper.closeDB();
				return true;
			}
			ClockDBHelper.closeDB();
		}
		return false;
	}

	public Boolean setGetUpTip(String userName, String friendName, String tip) throws SQLException {
		Connection conn  = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		int ret = clockDataDBManager.executeSetGetUpTip(userName, friendName, tip);
		ClockDBHelper.closeDB();
		if (ret != 0) {
			return true;
		}
		return false;
	}
	
	public Boolean setUserInfo(String userName, String nickname, String brief_intro) throws SQLException {

		// 获取Sql查询语句\
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		int ret = clockDataDBManager.executeSetUserInfo(userName, nickname, brief_intro);
		ClockDBHelper.closeDB();
		if(ret !=0)
			return true;
		else
			return false;
	}

	public boolean registSleepTime(String userName, String hour, String date) throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		int ret = clockDataDBManager.executeRegistSleepTime(userName, hour, date);
		ClockDBHelper.closeDB();
		if (ret != 0) {		
			return true;
		}
		return false;
	}

	public boolean getSleepTime(String userName, AppUserInfo appUserInfo) throws SQLException {
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		String sleepList = "";
		// 操作DB对象
		String column = "*";
		String table = "sleepTime";
		ResultSet rs = clockDataDBManager.executeNameQuery(column, table, userName);
		while (rs.next()) {
			String tmp = rs.getString("day") + " " + rs.getString("sleep") + "#";
			sleepList += tmp;
		}
		appUserInfo.setSleepList(sleepList);
		return true;
	}

	public boolean getGetUpTip(String userName, AppUserInfo appUserInfo) throws SQLException {
		// TODO Auto-generated method stub
		
		
		Connection conn = ClockDBHelper.createInstance();
		ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
		// 操作DB对象
		try {
			ResultSet rs = clockDataDBManager.executeGetGetUpTip(userName);
			if(rs.last()) {
				String greeting=rs.getString("greeting_text");
				String send_user = rs.getString("nickname");
				String greetingInfo = greeting+"#"+send_user;
				appUserInfo.setGreetingInfo(greetingInfo);
				System.out.println(greetingInfo);
			}
	        ClockDBHelper.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ClockDBHelper.closeDB();
		return false;
	}
}
