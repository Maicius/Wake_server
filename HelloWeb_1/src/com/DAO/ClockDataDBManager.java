package com.DAO;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClockDataDBManager extends ClockDBHelper{
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
		
        preparedStatement = dbConnection.prepareStatement(logSql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        setRs(preparedStatement.executeQuery());
		return getRs();
	}
	
	//根据username和表名进行查询
	public ResultSet executeNameQuery(String column, String table,String userName) throws SQLException{
		String querySql = "select "+column+" from "+ table +" where username = ?";
		preparedStatement  = dbConnection.prepareStatement(querySql);
	    preparedStatement.setString(1, userName);
	    setRs(preparedStatement.executeQuery());
		return getRs();
	}
	
	//获取好友列表
	public ResultSet executeFriendsListQuery(String userName) throws SQLException{
		String query = "select username, nickname, brief_intro from appuser where username in "
				+ "(select friends from friend_list"
				+ " where user_id = ?)";
		preparedStatement = dbConnection.prepareStatement(query);
		preparedStatement.setString(1, userName);
		setRs(preparedStatement.executeQuery());
		return getRs();
	}
	
	//搜索好友0
    public ResultSet executeSearchFriends(String column, String name) throws SQLException{
		String query = "select username, nickname from appuser where "+ column +"= ?";
		preparedStatement = dbConnection.prepareStatement(query);
		preparedStatement.setString(1, column);
		preparedStatement.setString(2, name);
		setRs(preparedStatement.executeQuery());
		return getRs();
	}

	//检查是否为好友关系
	public ResultSet executeWhetherFriendsQuery(String userName, String friendName) throws SQLException{
		String sql = "select * from friend_list where user_id= ? and friends= ?";
		preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, friendName);
		setRs(preparedStatement.executeQuery());
		return getRs();
	}
	
	//添加好友
	public int executeAddFriend(String userName, String friendName) throws SQLException{
		String sql = "insert into friend_list(user_id, friends) values(?,?);";
		preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1,userName);
		preparedStatement.setString(2, friendName);
		int ans = preparedStatement.executeUpdate();
		return ans;
	}
	
	//注册用户信息
	public int executeRegistUser(String userName, String password, String nickname, String brief_intro) throws SQLException{
		String regSql = "insert into appuser "
				+ "(username,password,nickname,brief_intro) "
				+ "values(?, ?, ?, ?) ";
		preparedStatement = dbConnection.prepareStatement(regSql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, nickname);
		preparedStatement.setString(4, brief_intro);
		int ans = preparedStatement.executeUpdate();
		return ans;
	}
	
	//注册起床时间
	public int executeRegistGetUpTime(String userName, Timestamp date) throws SQLException{
		String regTimeSql = "insert into getuptime (username, up_time) values(?, ?)";
		preparedStatement = dbConnection.prepareStatement(regTimeSql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, String.valueOf(date));
		int rs = preparedStatement.executeUpdate();
		return rs;
	}
	
	//设置问候语
	public int executeSetGetUpTip(String userName, String friendName, String tip) throws SQLException{
		String sql = "insert into greeting(send_user, receive_user, greeting_text)"
				+ " values(?, ?,?)";
		preparedStatement = dbConnection.prepareStatement(sql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, friendName);
		preparedStatement.setString(3, tip);
		int ret = preparedStatement.executeUpdate();
		return ret;
	}
	
	//删除好友
	public int executeDeleteFriend(String userName, String friendName) throws SQLException{
		String delSql = "delete from friend_list where user_id= ? and friends= ?";
		preparedStatement = dbConnection.prepareStatement(delSql);
		preparedStatement.setString(1, userName);
		preparedStatement.setString(2, friendName);
		int rs = preparedStatement.executeUpdate();
		return rs;
	}
	
	//设置用户信息
	public int executeSetUserInfo(String userName, String nickName, String brief_intro) throws SQLException{
		String SqlInfo = "update appuser set nickname = ?,brief_intro= ?"
				+ "where username =?";
		preparedStatement = dbConnection.prepareStatement(SqlInfo);
		preparedStatement.setString(1, nickName);
		preparedStatement.setString(2, brief_intro);
		preparedStatement.setString(3, userName);
		int ret = preparedStatement.executeUpdate();
		return ret;
	}
	
	public int executeRegistSleepTime(String userName, String hour,String date) throws SQLException{
		String regTimeSql = "insert into sleepTime (username, sleep, day) values(?, ?, ?) ";
		preparedStatement = dbConnection.prepareStatement(regTimeSql);
		preparedStatement.setString(1,  userName);	preparedStatement.setString(2, hour);
		preparedStatement.setString(3, date);
		int ret = preparedStatement.executeUpdate();
		return ret;
	}
	
    public ResultSet executeGetGetUpTip(String userName) throws SQLException{
    	String sql = "select greeting_text, nickname from greeting,appuser "
    			+ "where send_user = username and receive_user = ?";
    	preparedStatement = dbConnection.prepareStatement(sql);
    	preparedStatement.setString(1, userName);
    	setRs(preparedStatement.executeQuery());
    	return getRs();
    }
    

	
	
	
}
