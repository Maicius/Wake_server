package com.DAO;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ClockDataDBManager extends ClockDBManager{
    private ResultSet rs = null;
    
	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
	public ClockDataDBManager(Connection dbConnection) {
		super(dbConnection);
	}
	
	//根据username, password进行查询
	//登陆查询
	public ResultSet executeLogQuery(String username, String password) throws SQLException{
		String logSql = "select * from appuser where username = ? and password = ?";
		
        PreparedStatement preparedStatement = dbConnection.prepareStatement(logSql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        setRs(preparedStatement.executeQuery());
        preparedStatement.close();
		return getRs();
	}
	
	//根据username和表名进行查询
	public ResultSet executeNameQuery(String column, String table,String username) throws SQLException{
		String querySql = "select ? from ? where username = ?";
		PreparedStatement preparedStatement  = dbConnection.prepareStatement(querySql);
		preparedStatement.setString(1, column);
	    preparedStatement.setString(2, table);
	    preparedStatement.setString(3, username);
	    setRs(preparedStatement.executeQuery());
	    preparedStatement.close();
		return getRs();
	}
	
	//获取好友列表
	public ResultSet executeFriendsListQuery(String username) throws SQLException{
		String query = "select username, nickname, brief_intro from appuser where username in "
				+ "(select friends from friend_list"
				+ " where user_id = ?')";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
		preparedStatement.setString(1, username);
		setRs(preparedStatement.executeQuery());
		return getRs();
	}
	
	//搜索好友0
    public ResultSet executeSearchFriends(String column, String name) throws SQLException{
		String query = "select username, nickname from appuser where ? = ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
		preparedStatement.setString(1, column);
		preparedStatement.setString(2, name);
		setRs(preparedStatement.executeQuery());
		preparedStatement.close();
		return getRs();
	}

	//检查是否为好友关系
	public ResultSet executeWhetherFriendsQuery(String username, String friendName) throws SQLException{
		String sql = "select * from friend_list where user_id= ? and friends= ?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, friendName);
		setRs(preparedStatement.executeQuery());
		preparedStatement.close();
		return getRs();
	}
	
	//添加好友
	public int executeAddFriend(String userName, String friendName) throws SQLException{
		String sql = "insert into friend_list(user_id, friends) values(?,?);";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1,userName);
		preparedStatement.setString(2, friendName);
		int ans = preparedStatement.executeUpdate();
		preparedStatement.close();
		return ans;
	}
	
	//注册用户信息
	public int executeRegistUser(String username, String password, String nickname, String brief_intro) throws SQLException{
		String regSql = "insert into appuser "
				+ "(username,password,nickname,brief_intro) "
				+ "values(?, ?, ?, ?) ";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(regSql);
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, nickname);
		preparedStatement.setString(4, brief_intro);
		int ans = preparedStatement.executeUpdate();
		preparedStatement.close();
		return ans;
	}
	
	//注册起床时间
	public int executeRegistGetUpTime(String userName, Timestamp date) throws SQLException{
		String regTimeSql = "insert into getuptime (username, up_time) values(?, ?)";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(regTimeSql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, String.valueOf(date));
		int rs = preparedStatement.executeUpdate();
		preparedStatement.close();
		return rs;
	}
	
	//设置问候语
	public int executeSetGetUpTip(String userName, String friendName, String tip) throws SQLException{
		String sql = "insert into greeting(send_user, receive_user, greeting_text)"
				+ " values(?, ?,?)";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, friendName);
		preparedStatement.setString(3, tip);
		int ret = preparedStatement.executeUpdate();
		preparedStatement.close();
		return ret;
	}
	
	//删除好友
	public int executeDeleteFriend(String userName, String friendName) throws SQLException{
		String delSql = "delete from friend_list where user_id='" + userName + "' and friends='" + friendName + "'";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(delSql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, friendName);
		int rs = preparedStatement.executeUpdate();
		preparedStatement.close();
		return rs;
	}
	
	//设置用户信息
	public int executeSetUserInfo(String userName, String nickName, String brief_intro) throws SQLException{
		String SqlInfo = "update appuser set nickname= ?,brief_intro='?"
				+ "where username =?";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(SqlInfo);
		preparedStatement.setString(3, userName);
		preparedStatement.setString(1, nickName);
		preparedStatement.setString(2, brief_intro);
		
		int ret = preparedStatement.executeUpdate();
		preparedStatement.close();
		return ret;
	}
	
	public int executeRegistUserInfo(String userName, String hour,String date) throws SQLException{
		String regTimeSql = "insert into sleepTime (username, sleep, day) values(?, ?, ?) ";
		PreparedStatement preparedStatement = dbConnection.prepareStatement(regTimeSql);
		preparedStatement.setString(1,  userName);
		preparedStatement.setString(2, hour);
		preparedStatement.setString(3, date);
		
		int ret = preparedStatement.executeUpdate();
		preparedStatement.close();
		return ret;
	}
	
	
	
}
