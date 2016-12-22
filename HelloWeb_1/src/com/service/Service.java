package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

public class Service {
	
	public String timeList="";
    public String userinfo="";
	public Boolean login(String username, String password) {

		// 获取Sql查询语句
		userinfo="";
		String nickname="";
		String logSql = "select * from appuser where username ='" + username
				+ "' and password ='" + password + "'";

		// 获取DB对象
		DBManager sql = DBManager.createInstance();
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

	public Boolean register(String username, String password, String nickname) {
		
		if (checkName(username)){
			return false;
		}
             
		// 获取Sql查询语句
		String regSql = "insert into appuser "
				+ "(username,password,nickname) "
				+ "values('"
				+ username + "','" + password + "','"+ nickname+"') ";
		System.out.println(regSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
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
		int id;
		do{
			id =1+(int)(Math.random()*100000);
		}
		while(checkID(id));
             
		// 获取Sql查询语句
		String regTimeSql = "insert into getuptime (time_id, username, up_time) values('"+id+"','"
				+ username + "','" + date + "') ";
		System.out.println(regTimeSql);
		// 获取DB对象
		DBManager sql = DBManager.createInstance();
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
		DBManager sql = DBManager.createInstance();
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
		DBManager sql = DBManager.createInstance();
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
	
public Boolean setUserInfo(String username, String nickname, String brief_intro) {
	
			// 获取Sql查询语句
			String SqlUpdate = "update appuser set nickname='"+nickname+"',brief_intro='"+brief_intro+"'where username ='"+username+"'";
            System.out.println(SqlUpdate);
			// 获取DB对象
			DBManager sql = DBManager.createInstance();
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
}
