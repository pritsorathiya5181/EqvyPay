package com.eqvypay.service.friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.User;
import com.eqvypay.util.constants.Environment;

@Service
public class FriendService implements FriendRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    private UserDataManipulation userDataManipulation;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendDataManipulation friendDataManipulation;

    @Override
    public void addFriendByEmail(User user, String email) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        ResultSet result = null;
        User friend = null;
        try {
            friend = userRepository.getByEmail(email);

            friendDataManipulation.createTable();

            if (friend.getEmail() == null) {
                System.out.println("No user with email '" + email + "' found.");
            } else {
                PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
                insertQuery.setString(1, user.getUuid().toString());
                insertQuery.setString(2, friend.getUuid().toString());
                insertQuery.execute();
                System.out.println("Friend added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Enter a valid email id of a registered user!" + e);
        }

    }


    @Override
    public void addFriendByContact(User user, String contact) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        ResultSet result = null;
        try {
            PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
            selectQuery.setString(1, contact);
            result = selectQuery.executeQuery();

            String friendUuid = null;
            int count = DtoUtils.getCountOfRecords(result);
            selectQuery = connection.prepareStatement("select * from Users where contact = ?");
            selectQuery.setString(1, contact);
            result = selectQuery.executeQuery();

            if (count == 0) {
                System.out.println("No user with contact " + contact + " is registered in the system. Please try again");
            } else {

                if (result.next()) {
                    friendUuid = result.getString("uuid");
                }
                friendDataManipulation.createTable();

                PreparedStatement insertQuery = connection.prepareStatement("insert into Friend (user_id, friend_id) values (?, ?)");
                insertQuery.setString(1, user.getUuid().toString());
                insertQuery.setString(2, friendUuid);
                insertQuery.execute();
                System.out.println("Friend added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Enter a valid contact number of a registered user!");
        }
    }

    @Override
    public void removeFriendByEmail(User user, String email) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        ResultSet result = null;
        try {
            if (DtoUtils.tableExist(dcms, "Friend")) {
                PreparedStatement selectQuery = connection.prepareStatement("select * from Users where email = ?");
                selectQuery.setString(1, email);
                result = selectQuery.executeQuery();

                String friendUuid = null;
                while (result.next()) {
                    friendUuid = result.getString("uuid");
                }
                PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
                insertQuery.setString(1, friendUuid);
                insertQuery.execute();
            } else {
                System.out.println("No friend found");
            }
        } catch (SQLException e) {
            System.out.println("Enter a valid email id of a registered user!");
        }
    }


    @Override
    public void removeFriendByContact(User user, String contact) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        ResultSet result = null;
        try {
            if (DtoUtils.tableExist(dcms, "Friend")) {
                PreparedStatement selectQuery = connection.prepareStatement("select * from Users where contact = ?");
                selectQuery.setString(1, contact);
                result = selectQuery.executeQuery();

                String friendUuid = null;
                while (result.next()) {
                    friendUuid = result.getString("uuid");
                }
                PreparedStatement insertQuery = connection.prepareStatement("delete from Friend where friend_id = ?");
                insertQuery.setString(1, friendUuid);
                insertQuery.execute();
            } else {
                System.out.println("No friend found");
            }

        } catch (SQLException e) {
            System.out.println("Enter a valid contact number of a registered user!");
        }
    }

}
