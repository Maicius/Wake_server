package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  class ClockDBHelper {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "";
	public static final String PASS = "";
	public static final String URL = "jdbc:mysql://116.62.41.211/clock?useUnicode=true&characterEncoding=UTF-8";

	//public static final String URL = "jdbc:mysql://116.62.41.211:3306/clock";
	//protected static Connection ClockConn = null;
	protected static PreparedStatement preparedStatement = null;
	protected static Connection dbConnection = null;
    public ClockDBHelper(Connection dbConnection) {
        ClockDBHelper.dbConnection = dbConnection;
    }
    
	public static Connection createInstance() throws SQLException {
        initDB();
        dbConnection = DriverManager.getConnection(URL, USER, PASS);
		System.out.println("SqlManager:Connect to database successful.");
        return dbConnection;
	}

	// 加载驱动
	public static void initDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Initial Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 关闭数据库 关闭对象，释放句柄
	public static void closeDB() {
		System.out.println("Close connection to database..");
		try {
			preparedStatement.close();
			dbConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Close connection successful");
	}

}
