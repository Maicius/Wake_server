package com.DAO;

import java.sql.*;

public class ClockDBHelper {
	// 数据库连接常量
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String USER = "root";
	public static final String PASS = "110110";
	public static final String URL = "jdbc:mysql://localhost/clock?useUnicode=true&characterEncoding=UTF-8";

	//public static final String URL = "jdbc:mysql://116.62.41.211:3306/clock";
	protected static Connection ClockConn = null;
	protected static PreparedStatement preparedStatemen = null;
    public ClockDBHelper() {
    }
    
	public static Connection createInstance() throws SQLException {
        initDB();
		ClockConn = DriverManager.getConnection(URL, USER, PASS);
		System.out.println("SqlManager:Connect to database successful.");
        return ClockConn;
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
			ClockConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Close connection successful");
	}
    
}
