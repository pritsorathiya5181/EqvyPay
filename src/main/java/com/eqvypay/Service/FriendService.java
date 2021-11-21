package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.eqvypay.Persistence.User;
import com.eqvypay.util.constants.Environment;

public class FriendService {

	public void addFriend(User user) throws Exception{
		
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		while(true) {
			System.out.println("**********************Add Friend************************");
			System.out.println("[1] Add friend by email");
			System.out.println("[2] Add friend by phone number");
			System.out.println("[3] Exit");
			try {
				input = scanner.nextLine();
			} catch (Exception e1) {
			}

			Integer option = 0;
			try {
				option = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Unknown Input. Please try again!");
				continue;
			}
			
			DatabaseConnectionManagementService connectionManagement = new DatabaseConnectionManagementService();
			Connection connection = connectionManagement.getConnection(Environment.DEV);
	
			switch (option) {
				case 1:
					System.out.println("Enter your friend's email id");
					String friendEmail = scanner.nextLine();
					ResultSet result = null;
					try {
						PreparedStatement selectQuery = connection.prepareStatement("select * from Users where email = ?");
						selectQuery.setString(1, friendEmail);
						result = selectQuery.executeQuery();
					}catch(SQLException e) {
						System.out.println("Enter a valid email id of a registered user!");
					}

					String friendUuid = null;
					while(result.next()) {
						friendUuid = result.getString("uuid");
					}
					PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
					insertQuery.setString(1, user.getUuid().toString());
					insertQuery.setString(2, friendUuid);
					insertQuery.execute();
					break;
				case 2:
					System.out.println("Enter your friend's contact number");
					String friendContactNo = scanner.nextLine();
					result = null;
					try {
						PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
						selectQuery.setString(1, friendContactNo);
						result = selectQuery.executeQuery();
					}catch(SQLException e) {
						System.out.println("Enter a valid contact number of a registered user!");
					}

					friendUuid = null;
					while(result.next()) {
						friendUuid = result.getString("uuid");
					}
					insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
					insertQuery.setString(1, user.getUuid().toString());
					insertQuery.setString(2, friendUuid);
					insertQuery.execute();
					break;
				case 3:
					break;
			}
			scanner.close();
			
			break;	
		}
	}
	
}
