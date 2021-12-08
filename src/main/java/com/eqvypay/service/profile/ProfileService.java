package com.eqvypay.service.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.eqvypay.service.authentication.AuthenticationService;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.user.UserFactory;
import com.eqvypay.service.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;
import com.eqvypay.util.constants.Environment;

@Service
public class ProfileService implements ProfileRepository {
	
	@Autowired
	DatabaseConnectionManagementService dcms;

    @Autowired
    private UserFactory userFactory;
    
    @Override
	public void updateUsername(IUser user, String username) throws Exception {
    	UserRepository userRepository = userFactory.getUserRepository();
    	Connection connection = dcms.getConnection(Environment.DEV);
	
		PreparedStatement updateStatement = connection.prepareStatement("update Users set name=? where email=?");
		updateStatement.setString(1, username);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();				
	}

	@Override
	public void updateContact(IUser user, String contact) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		
		PreparedStatement updateStatement = connection.prepareStatement("update Users set contact=? where email=?");
		updateStatement.setString(1, contact);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();
	}

	@Override
	public void updatePassword(IUser user, String password) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		String hashedPassword = AuthenticationService.getHashedPassword(password);
		PreparedStatement updateStatement = connection.prepareStatement("update Users set password=? where email=?");
		updateStatement.setString(1, hashedPassword);
		updateStatement.setString(2, user.getEmail());
		
		boolean update = updateStatement.execute();
	}

}
