package com.eqvypay.service.activity;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Activity;
import com.eqvypay.persistence.IActivity;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;

@Service
public class ActivityService implements ActivityRepository {

	@Autowired
	private DatabaseConnectionManagementService dcms;
	
	@Override
	public boolean addActivity(IActivity activity) throws Exception {
		Connection connection = dcms.getConnection(dcms.parseEnvironment());
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Activity (uuid,userId,message) VALUES (?,?,?)");
		statement.setString(1, UUID.randomUUID().toString());
		statement.setString(2, activity.getUserId());
		statement.setString(3, activity.getMessage());
		statement.executeUpdate();
		return true;
	}

	@Override
	public List<IActivity> getUserActivity(String userId) throws Exception {
		Connection connection = dcms.getConnection(dcms.parseEnvironment());
		PreparedStatement statement = connection.prepareStatement("SELECT * from Activity where userId = ?");
		statement.setString(1, userId);
		ResultSet resultSet = statement.executeQuery();
		return DtoUtils.getActivityFromResultSet(resultSet);
	}

	@Override
	public boolean deleteActivity(String uuid) throws Exception {
		Connection connection = dcms.getConnection(dcms.parseEnvironment());
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Activity where uuid = ?");
		statement.setString(1, uuid);
		int result = statement.executeUpdate();
		return true;
	}
	
	@Override
	public void createTable() throws Exception {
		Connection connection = dcms.getConnection(dcms.parseEnvironment());
		PreparedStatement statement = connection.prepareStatement("CREATE TABLE Activity(uuid varchar(255) primary key,userId varchar(255),message varchar(255));");
		statement.executeUpdate();
	}

}
