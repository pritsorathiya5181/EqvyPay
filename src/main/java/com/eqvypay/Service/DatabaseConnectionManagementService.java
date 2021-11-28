package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.DriverManager;

import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;

public class DatabaseConnectionManagementService {

	public Connection getConnection(Environment env) throws Exception {
		switch(env) {
		case TEST:
			return DriverManager.getConnection(DatabaseConstants.TEST_URL,DatabaseConstants.TEST_USERNAME,DatabaseConstants.TEST_PASSWORD);
		case DEV:
			return DriverManager.getConnection(DatabaseConstants.DEV_URL,DatabaseConstants.DEV_USERNAME,DatabaseConstants.DEV_PASSWORD);	
		case PROD:
			return DriverManager.getConnection(DatabaseConstants.PROD_URL,DatabaseConstants.PROD_USERNAME,DatabaseConstants.PROD_PASSWORD);
		default:	
			throw new Exception("Unable to get a connection object for env :"+env.getEnvironment());
		}
	}
}
