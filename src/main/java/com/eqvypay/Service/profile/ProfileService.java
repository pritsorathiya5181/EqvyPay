package com.eqvypay.Service.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eqvypay.Service.authentication.AuthenticationService;
import com.eqvypay.Service.database.DatabaseConnectionManagementService;
import com.eqvypay.Service.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.Persistence.User;
import com.eqvypay.util.constants.Environment;

@Service
public class ProfileService implements ProfileRepository{
	
	@Autowired
	DatabaseConnectionManagementService dcms;

    @Autowired
    private UserRepository userRepo;
	
	@Override
	public void updateUsername(User user, String username) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
	
		PreparedStatement updateStatement = connection.prepareStatement("update Users set name=? where email=?");
		updateStatement.setString(1, username);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();				
	}

	@Override
	public void updateContact(User user, String contact) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		
		PreparedStatement updateStatement = connection.prepareStatement("update Users set contact=? where email=?");
		updateStatement.setString(1, contact);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();
	}

	@Override
	public void updatePassword(User user, String password) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		String hashedPassword = AuthenticationService.getHashedPassword(password);
		PreparedStatement updateStatement = connection.prepareStatement("update Users set password=? where email=?");
		updateStatement.setString(1, hashedPassword);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();
	}

}
