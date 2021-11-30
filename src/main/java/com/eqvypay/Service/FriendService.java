package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.Persistence.User;
import com.eqvypay.util.constants.Environment;

@Service
public class FriendService implements FriendRepository{

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    private UserRepository userRepo;
    
	@Override
	public void addFriendByEmail(User user, String email) throws Exception{
		Connection connection = dcms.getConnection(Environment.DEV);
		ResultSet result = null;
		User friend = null;
		try {
			friend = userRepo.getByEmail(email);
		}catch(SQLException e) {
			System.out.println("Enter a valid email id of a registered user!" + e);
		}

		String friendUuid = null;
		PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
		insertQuery.setString(1, user.getUuid().toString());
		insertQuery.setString(2, friend.getUuid().toString());
		insertQuery.execute();
	}
	

	@Override
	public void addFriendByContact(User user, String contact) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		ResultSet result = null;
		try {
			PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
			selectQuery.setString(1, contact);
			result = selectQuery.executeQuery();
		}catch(SQLException e) {
			System.out.println("Enter a valid contact number of a registered user!");
		}

		String friendUuid = null;
		while(result.next()) {
			friendUuid = result.getString("uuid");
		}
		PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
		insertQuery.setString(1, user.getUuid().toString());
		insertQuery.setString(2, friendUuid);
		insertQuery.execute();
	}
	
	@Override
	public void removeFriendByEmail(User user, String email) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		ResultSet result = null;
		try {
			PreparedStatement selectQuery = connection.prepareStatement("select * from Users where email = ?");
			selectQuery.setString(1, email);
			result = selectQuery.executeQuery();
		}catch(SQLException e) {
			System.out.println("Enter a valid email id of a registered user!");
		}
	
		String friendUuid = null;
		while(result.next()) {
			friendUuid = result.getString("uuid");
		}
		PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
		insertQuery.setString(1, friendUuid);
		insertQuery.execute();				
		
	}


	@Override
	public void removeFriendByContact(User user, String contact) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		ResultSet result = null;
		try {
			PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
			selectQuery.setString(1, contact);
			result = selectQuery.executeQuery();
		}catch(SQLException e) {
			System.out.println("Enter a valid contact number of a registered user!");
		}

		String friendUuid = null;
		while(result.next()) {
			friendUuid = result.getString("uuid");
		}
		PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
		insertQuery.setString(1, friendUuid);
		insertQuery.execute();
	}
	
}
