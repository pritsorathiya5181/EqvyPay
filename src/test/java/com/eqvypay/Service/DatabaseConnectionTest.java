package com.eqvypay.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.Test;

import com.eqvypay.util.constants.Environment;

public class DatabaseConnectionTest {

	@Test
	public void shouldInstantiateDatabaseConnection() {
		assertNotNull(new DatabaseConnectionManagementService());
	}
	
	@Test
	public void shouldCheckConnectionForTestDb() throws Exception {
		DatabaseConnectionManagementService dcms = new DatabaseConnectionManagementService();
		Connection connection = dcms.getConnection(Environment.TEST);
		assertFalse(connection.isClosed());
		connection.close();
	}
	
	@Test
	public void shouldCheckConnectionForDevDb() throws Exception {
		DatabaseConnectionManagementService dcms = new DatabaseConnectionManagementService();
		Connection connection = dcms.getConnection(Environment.DEV);
		assertFalse(connection.isClosed());
		connection.close();
		
	}

	@Test
	public void shouldCheckConnectionForProdDb() throws Exception {
		DatabaseConnectionManagementService dcms = new DatabaseConnectionManagementService();
		Connection connection = dcms.getConnection(Environment.PROD);
		assertFalse(connection.isClosed());
		connection.close();
	}
	
}