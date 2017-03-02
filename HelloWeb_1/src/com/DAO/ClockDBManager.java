package com.DAO;

import java.sql.Connection;

public abstract class ClockDBManager {
	protected Connection dbConnection = null;
    public ClockDBManager(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

}
