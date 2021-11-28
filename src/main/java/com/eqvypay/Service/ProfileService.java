package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.eqvypay.Persistence.User;
import com.eqvypay.util.constants.Environment;

public class ProfileService {
	
	public void getProfile(User user) throws Exception {
		DatabaseConnectionManagementService connectionManagement = new DatabaseConnectionManagementService();
		Connection connection = connectionManagement.getConnection(Environment.DEV);
		PreparedStatement selectQuery = connection.prepareStatement("select * from Users where email = ?");
		selectQuery.setString(1, user.getEmail());
		ResultSet result = selectQuery.executeQuery();
		
		System.out.println("\nProfile Details for " + user.getName());
		while(result.next()) {
			System.out.println("Username: " + result.getString("name"));
			System.out.println("Email id: " + result.getString("email"));
			System.out.println("Contact number: " + result.getString("contact"));
		}
	}
	public void updateProfile(User user) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		while(true) {
			System.out.println("\n**********************Update Profile************************");
			System.out.println("Which information would you like to update?");
			System.out.println("[1] Update username");
			System.out.println("[2] Update contact number");
			System.out.println("[3] Update password");
			System.out.println("[4] Exit");
			try {
				input = scanner.nextLine();
			} catch (Exception e1) {
			}

			Integer option = 0;
			try {
				option = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("\nUnknown Input. Please try again!\n");
				continue;
			}
			
			if(option == 4) {
				break;
			}
			
			DatabaseConnectionManagementService connectionManagement = new DatabaseConnectionManagementService();
			Connection connection = connectionManagement.getConnection(Environment.DEV);
	
			switch (option) {
				case 1:
					System.out.println("Enter the new username");
					String newUsername = scanner.nextLine();
					PreparedStatement updateStatement = connection.prepareStatement("update Users set name=? where email=?");
					updateStatement.setString(1, newUsername);
					updateStatement.setString(2, user.getEmail());
					
					boolean update = updateStatement.execute();
					break;
				case 2:
					System.out.println("Enter the new contact number");
					String newContact = scanner.nextLine();
					updateStatement = connection.prepareStatement("update Users set contact=? where email=?");
					updateStatement.setString(1, newContact);
					updateStatement.setString(2, user.getEmail());
					
					update = updateStatement.execute();
					break;
				case 3: 
					System.out.println("Enter the new password");
					String newPassword = scanner.nextLine();
					updateStatement = connection.prepareStatement("update Users set password=? where email=?");
					updateStatement.setString(1, newPassword);
					updateStatement.setString(2, user.getEmail());
					
					update = updateStatement.execute();
				default:
					break;
			}
			
		}
	}

}
