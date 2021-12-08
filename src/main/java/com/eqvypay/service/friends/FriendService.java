package com.eqvypay.service.friends;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.eqvypay.service.activity.ActivityHelper;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.user.IUserDataManipulation;
import com.eqvypay.service.user.UserFactory;
import com.eqvypay.util.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IUser;
import com.eqvypay.util.constants.Constants;
import com.eqvypay.util.constants.Environment;

@Service
public class FriendService implements FriendRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    private UserFactory userFactory;
    
    @Override
    public void addFriendByEmail(IUser user, String email) throws Exception {
    	IUserDataManipulation userDataManipulation = userFactory.getUserDataManipuation();
        Connection connection = dcms.getConnection(Environment.DEV);
        ResultSet result = null;
        IUser friend = null;
        friend = userDataManipulation.getByEmail(email);

        if (friend.getEmail() == null) {
            System.out.println("No user with email '" + email + "' found.");
        } else {
            PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
            insertQuery.setString(1, user.getUuid().toString());
            insertQuery.setString(2, friend.getUuid().toString());
            insertQuery.execute();
            System.out.println("Friend added successfully.");
        }
        ActivityHelper.addActivity(user.getUuid().toString(), Constants.friends.formatted(friend.getName()));
        ActivityHelper.addActivity(friend.getUuid().toString(), Constants.friends.formatted(user.getName()));
    }


    @Override
    public void addFriendByContact(IUser user, String contact) throws Exception {
    	IUserDataManipulation userDataManipulation = userFactory.getUserDataManipuation();
        
        Connection connection = dcms.getConnection(Environment.DEV);
        ResultSet result = null;
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
            selectQuery.setString(1, contact);
            result = selectQuery.executeQuery();
        } catch (SQLException e) {
            System.out.println("Enter a valid contact number of a registered user!");
        }

        String friendUuid = null;
        IUser friend = null;
        
        int count = DtoUtils.getCountOfRecords(result);
        PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
        selectQuery.setString(1, contact);
        result = selectQuery.executeQuery();

        if (count == 0) {
            System.out.println("No user with contact " + contact + " is registered in the system. Please try again");
        } else {

            if (result.next()) {
                friendUuid = result.getString("uuid");
                friend = userDataManipulation.getByUuid(UUID.fromString(friendUuid));
                
            }
            System.out.println("FID: " + friendUuid);

            PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
            insertQuery.setString(1, user.getUuid().toString());
            insertQuery.setString(2, friendUuid);
            insertQuery.execute();
            System.out.println("Friend added successfully.");

        }
        
        ActivityHelper.addActivity(user.getUuid().toString(), Constants.friends.formatted(friend.getName()));
        ActivityHelper.addActivity(friend.getUuid().toString(), Constants.friends.formatted(user.getName()));

    }

    @Override
    public void removeFriendByEmail(IUser user, String email) throws Exception {
    	IUserDataManipulation userDataManipulation = userFactory.getUserDataManipuation();
        Connection connection = dcms.getConnection(Environment.DEV);
        ResultSet result = null;
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select * from Users where email = ?");
            selectQuery.setString(1, email);
            result = selectQuery.executeQuery();
        } catch (SQLException e) {
            System.out.println("Enter a valid email id of a registered user!");
        }

        String friendUuid = null;
        IUser friend = null;
        while (result.next()) {
            friendUuid = result.getString("uuid");
            friend = userDataManipulation.getByUuid(UUID.fromString(friendUuid));
        }
        PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
        insertQuery.setString(1, friendUuid);
        insertQuery.execute();
        ActivityHelper.addActivity(user.getUuid().toString(), Constants.removeFriend.formatted(friend.getName()));
        ActivityHelper.addActivity(friend.getUuid().toString(),Constants.removeFriend.formatted(user.getName()));
  
    }


    @Override
    public void removeFriendByContact(IUser user, String contact) throws Exception {
    	IUserDataManipulation userDataManipulation = userFactory.getUserDataManipuation();
        Connection connection = dcms.getConnection(Environment.DEV);
        ResultSet result = null;
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
            selectQuery.setString(1, contact);
            result = selectQuery.executeQuery();
        } catch (SQLException e) {
            System.out.println("Enter a valid contact number of a registered user!");
        }

        String friendUuid = null;
        IUser friend = null;
        while (result.next()) {
            friendUuid = result.getString("uuid");
            friend = userDataManipulation.getByUuid(UUID.fromString(friendUuid));
        }
        PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
        insertQuery.setString(1, friendUuid);
        insertQuery.execute();
        
        ActivityHelper.addActivity(user.getUuid().toString(), Constants.removeFriend.formatted(friend.getName()));
        ActivityHelper.addActivity(friend.getUuid().toString(),Constants.removeFriend.formatted(user.getName()));
  
    }

}
