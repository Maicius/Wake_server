package com.checkInformation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.DAO.*;
/**
 * 
 * @author Maicius
 * Encoding = utf-8
 * 用于校验各种信息的工具类
 */
public class CheckInformation {
	
	public static Boolean whetherUser(String username) {
		try {
			Connection conn = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
			ResultSet rs = clockDataDBManager.executeNameQuery("*","appuser", username);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlErr) {
			sqlErr.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ClockDBHelper.closeDB();
		}
		return null;
	}

	public static Boolean whetherFriends(String userName, String friendName) {
		if (userName.equals("") || friendName.equals("")) {
			return false;
		}
		try {
			Connection conn = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
			ResultSet rs = clockDataDBManager.executeWhetherFriendsQuery(userName, friendName);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlErr) {
			sqlErr.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ClockDBHelper.closeDB();
		}
		return null;
	}
	
	public static Boolean checkNameExist(String username)
	{

		try {
			Connection conn  = ClockDBHelper.createInstance();
			ClockDataDBManager clockDataDBManager = new ClockDataDBManager(conn);
			ResultSet rs = clockDataDBManager.executeNameQuery("*", "appuser",username);
			if (rs.next()) {
				return true;
			}else{
				return false;
			}
		} catch (SQLException sqlErr) {
			sqlErr.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}finally{
			ClockDBHelper.closeDB();
		}
		return null;
	}

}
