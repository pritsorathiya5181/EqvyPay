package com.eqvypay.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.eqvypay.util.constants.Environment;

@SpringBootTest
public class DatabaseConnectionManagementServiceTest {
	
	@Autowired
	private DatabaseConnectionManagementService dcms;

	@org.junit.jupiter.api.Test 
	public void shouldInstantiateDatabaseConnection() {
		assertNotNull(new DatabaseConnectionManagementService());
	}
	
	@org.junit.jupiter.api.Test
	public void shouldCheckConnectionForTestDb() throws Exception {
		Connection connection = dcms.getConnection(Environment.TEST);
		assertFalse(connection.isClosed());
		connection.close();
	}
	
	@org.junit.jupiter.api.Test
	public void shouldCheckConnectionForDevDb() throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		assertFalse(connection.isClosed());
		connection.close();
		
	}

	@org.junit.jupiter.api.Test
	public void shouldCheckConnectionForProdDb() throws Exception {
		Connection connection = dcms.getConnection(Environment.PROD);
		assertFalse(connection.isClosed());
		connection.close();
	}
	
}