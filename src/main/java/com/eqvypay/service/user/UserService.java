package com.eqvypay.service.user;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IUser;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;

@Service
public class UserService implements UserRepository {

	@Autowired
	private DatabaseConnectionManagementService dcms;

	@Override
	public IUser getUserByEmailAndPassword(String email, String password) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'"+"AND password="+"'"+password+"'");
		return DtoUtils.getUserFromResultSet(resultSet);
	}

	@Override
	public IUser getByEmail(String email) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'");
		return DtoUtils.getUserFromResultSet(resultSet);
	}
	@Override
	public IUser getByUuid(UUID uuid) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE uuid ="+"'"+uuid.toString());
		return DtoUtils.getUserFromResultSet(resultSet);
	}

	@Override
	public void save(IUser user) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_USER);
		preparedStatement.setString(1, user.getUuid().toString());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setString(3, user.getEmail());
		preparedStatement.setString(4, user.getContact());
		preparedStatement.setString(5, user.getPassword());
		preparedStatement.setString(6, user.getSecurityAnswer());
		int count = preparedStatement.executeUpdate();
		if(count>0) {
//			System.out.println("user inserted");
		}
	}

	@Override
	public void delete(UUID userId) throws Exception{
		Connection connection = dcms.getConnection(Environment.DEV);
		PreparedStatement statement = connection.prepareStatement("DELETE from Users where uuid = ?");
		statement.setString(1, userId.toString());
		int count = statement.executeUpdate();
		if(count>0) {
//			System.out.println("user deleted");
		}
	}

	@Override
	public void createTable() throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement s = connection.createStatement();
		s.executeUpdate("CREATE TABLE Users"
				+ " ( uuid varchar(255) PRIMARY KEY"
				+ " ,name varchar(255)"
				+ " ,email varchar(255)"
				+ " ,contact varchar(255)"
				+ " ,password varchar(255)"
				+ " ,security_answer varchar(255) );"
		);
	}
}
