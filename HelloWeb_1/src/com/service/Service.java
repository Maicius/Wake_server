package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

public class Service {
	
	public String timeList="";
    public String userinfo="";
    public String friendsList="";
    public String sleepList="";
    public String greetingInfo="";
    DBManager sql = DBManager.createInstance();
	public Boolean login(String username, String password) {

		// 获取Sql查询语句
		userinfo="";
		String nickname="";
		String logSql = "select * from appuser where username ='" + username
				+ "' and password ='" + password + "'";

		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs= sql.executeQuery(logSql);
			if (rs.next()) {
				nickname = rs.getString("nickname");
				userinfo="success"+"#"+nickname;
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public Boolean register(String username, String password, String nickname, String brief_intro) {
		
		if (checkName(username)){
			return false;
		}
             
		// 获取Sql查询语句
		String regSql = "insert into appuser "
				+ "(username,password,nickname,brief_intro) "
				+ "values('"
				+ username + "','" + password + "','"+ nickname+"','"+brief_intro+"') ";
		System.out.println(regSql);
		sql.connectDB();
		
		int ret = sql.executeUpdate(regSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}
	
	public Boolean registerTime(String username, java.sql.Timestamp date){
             
		// 获取Sql查询语句
		String regTimeSql = "insert into getuptime (username, up_time) values('"
				+ username + "','" + date + "') ";
		//System.out.println(regTimeSql);
		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		int ret = sql.executeUpdate(regTimeSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}
	
	public Boolean getTimeHistory(String username) {
		
        timeList="";
       
		// 获取Sql查询语句
		String SqlQuery = "select up_time from getuptime where username ='" + username+"'";

		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			while (rs.next()) {
				String tmp=rs.getString("up_time").substring(0, 19)+"#" ;
				timeList+=tmp;
			}
			sql.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}
	
public Boolean getUserInfo(String username) {
		
        userinfo="";
		// 获取Sql查询语句
		String SqlQuery = "select username, nickname, brief_intro from appuser where username ='" + username+"'";

		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();

		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			if (rs.next()) {
				String user_name=rs.getString("username");
				String nickname=rs.getString("nickname");
				String brief_intro=rs.getString("brief_intro");
				userinfo=user_name+"#"+nickname+"#"+brief_intro;
			}
			sql.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
}

public Boolean getFriendsList(String username) {
	friendsList = "";
	String query = "select username, nickname, brief_intro from appuser where username in (select friends from friend_list"
			+ " where user_id = '" + username + "');";
	
	//DBManager sql = DBManager.createInstance();
	sql.connectDB();
	
	ResultSet rs = sql.executeQuery(query);
	try {
		while (rs.next()) {
			String userName = rs.getString("username");
			String nickName = rs.getString("nickname");
			String signature = rs.getString("brief_intro");
			friendsList += userName + "#" + nickName + "#" + signature + "#";
		}
		sql.closeDB();
		return true;
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	sql.closeDB();
	return false;
}

public Boolean deleteFriend(String userName, String friendName) {
	String query = "delete from friend_list where user_id='" + userName + "' and friends='" + friendName+ "'";
	
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

public Boolean searchFriend(String nickName, String userName) {
	String query = "";
	if (nickName.equals("") && userName.equals(""))//ͬʱΪ��
		return false;
	else if (nickName.equals("")) {                //�ǳ�Ϊ�գ����ǵ绰��Ϊ��
		query = "select username, nickname from appuser where username = '" + userName + "';";
	} else if (userName.equals("")) {              //ֻ���ǳ�
		query = "select username, nickname from appuser where nickname = '" + nickName + "';";
	} else {                                       //�ǳơ��û�������
		query = "select username, nickname from appuser where username = '"+ userName +"'"
				+ " and nickname='" + nickName + "';";
	}
	
	//DBManager sql = DBManager.createInstance();
	sql.connectDB();
	
	ResultSet rs = sql.executeQuery(query);
	try {
		while (rs.next()) {
			String uName = rs.getString("username");
			String nName = rs.getString("nickname");
			friendsList += uName + "#" + nName + "#";
		}
		sql.closeDB();
		return true;
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	sql.closeDB();
	return false;
}

public Boolean whetherUser(String name) {
	String query = "select * from appuser where username = '"+name+"';";

	//DBManager sql = DBManager.createInstance();
	sql.connectDB();
	
	ResultSet rs = sql.executeQuery(query);
	try {
		if (rs.next()){
			sql.closeDB();
			return true;
		} else {
			sql.closeDB();
			return false;
		}
		
	} catch (SQLException e) {
		// TODO �Զ����ɵ� catch ��
		e.printStackTrace();
	}
	sql.closeDB();
	return false;
}

public Boolean whetherFriends(String userName, String friendName) {
	String query = "";
	if (userName.equals("") || friendName.equals("")) {  //������Ϊ�յ����
		return false;
	} else {
		query = "select * from friend_list where user_id='"+userName+"' and friends='"+friendName+"';";
	}
	
	//DBManager sql = DBManager.createInstance();
	sql.connectDB();
	
	ResultSet rs = sql.executeQuery(query);
	try {
		if (rs.next()){
			sql.closeDB();
			return true;
		} else {
			sql.closeDB();
			return false;
		}
		
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
		
		//DBManager sql = DBManager.createInstance();
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

	private Boolean checkID(int id)
	{
		String checksql ="select time_id from getuptime where time_id ="+id;
		
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(checksql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}
	
	private Boolean checkName(String _name)
	{
		String checksql ="select * from appuser where username ='"+_name+"'";
		
		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(checksql);
			if (rs.next()) {
				sql.closeDB();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public boolean registerSleepTime(String username, String date) {
		// TODO Auto-generated method stub
		String regTimeSql = "insert into sleepTime (username, sleep) values('"
				+ username + "','" + date + "') ";
		System.out.println(regTimeSql);
		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();
		
		int ret = sql.executeUpdate(regTimeSql);
		if (ret != 0) {
			sql.closeDB();
			return true;
		}
		sql.closeDB();

		return false;
	}

	public boolean getSleepTime(String username) {
        timeList="";       
		// 获取Sql查询语句
		String SqlQuery = "select sleep from sleepTime where username ='" + username+"'";
		
		// 获取DB对象
		//DBManager sql = DBManager.createInstance();
		sql.connectDB();
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			while (rs.next()) {
				String tmp=rs.getString("sleep")+"#" ;
				sleepList+=tmp;
			}
			sql.closeDB();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql.closeDB();
		return false;
	}

	public boolean getGetUpTip(String username) {
		// TODO Auto-generated method stub
		String SqlQuery = "select greeting_text, nickname from greeting,appuser where send_user = username and receive_user ='" + username+"'";
		
		sql.connectDB();
		// 操作DB对象
		try {
			ResultSet rs = sql.executeQuery(SqlQuery);
			if(rs.last()) {
				String greeting=rs.getString("greeting_text");
				String send_user = rs.getString("nickname");
				greetingInfo = greeting+"#"+send_user;
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
