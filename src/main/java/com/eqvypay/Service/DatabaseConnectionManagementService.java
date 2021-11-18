package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;

@Service
public class DatabaseConnectionManagementService {

	@Autowired
	private org.springframework.core.env.Environment environment;
	
	public Connection getConnection(Environment env) throws Exception {
		switch(env) {
		case TEST:
			return DriverManager.getConnection(environment.getProperty( DatabaseConstants.TEST_URL ) ,environment.getProperty(DatabaseConstants.TEST_USERNAME) ,environment.getProperty( DatabaseConstants.TEST_PASSWORD));
		case DEV:
			return DriverManager.getConnection(environment.getProperty(DatabaseConstants.DEV_URL),environment.getProperty(DatabaseConstants.DEV_USERNAME),environment.getProperty(DatabaseConstants.DEV_PASSWORD));	
		case PROD:
			return DriverManager.getConnection(environment.getProperty(DatabaseConstants.PROD_URL),environment.getProperty(DatabaseConstants.PROD_USERNAME),environment.getProperty(DatabaseConstants.PROD_PASSWORD));
		default:	
			throw new Exception("Unable to get a connection object for env :"+env.getEnvironment());
		}
	}
}
