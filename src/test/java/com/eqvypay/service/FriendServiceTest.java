package com.eqvypay.service;

import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.friends.FriendDataManipulation;
import com.eqvypay.service.friends.FriendRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.constants.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootTest
public class FriendServiceTest {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDataManipulation userDataManipulation;

    @Autowired
    FriendDataManipulation friendDataManipulation;

    private static Connection connection;

//    @BeforeAll
//    public static void config() throws Exception {
//        userDataManipulation.createTable();
//        friendDataManipulation.createTable();
//
//        DatabaseConnectionManagementService dcms = new DatabaseConnectionManagementService();
//
//        connection = dcms.getConnection(Environment.TEST);
//
//        UserRepository userRepository = null;
//
//        User user = new User();
//        user.setName("Hirva");
//        user.setEmail("hirva@gmail.com");
//        user.setContact("7826404405");
//        user.setPassword("hirva");
//        userRepository.save(user);
//    }

    @Test
    @Order(1)
    public void testAddFriendByEmail() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        User friend = userRepository.getByEmail("prit@gmail.com");

        friendRepository.addFriendByEmail(user, friend.getEmail());
        PreparedStatement selectQuery = connection.prepareStatement("select * from Friend where user_id = ? and friend_id=?");
        selectQuery.setString(1, String.valueOf(user.getUuid()));
        selectQuery.setString(2, String.valueOf(friend.getUuid()));
        ResultSet result = selectQuery.executeQuery();
        Assertions.assertTrue(result.next());
    }

    @Test
    @Order(2)
    public void testAddFriendByContact() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        User friend = userRepository.getByEmail("jay@gmail.com");
        System.out.println("contact: " +friend.getContact());
        friendRepository.addFriendByContact(user, friend.getContact());
        PreparedStatement selectQuery = connection.prepareStatement("select * from Friend where user_id = ? and friend_id=?");
        selectQuery.setString(1, String.valueOf(user.getUuid()));
        selectQuery.setString(2, String.valueOf(friend.getUuid()));
        ResultSet result = selectQuery.executeQuery();
        Assertions.assertTrue(result.next());
    }

    @Test
    @Order(3)
    public void testRemoveFriendByEmail() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        User friend = userRepository.getByEmail("jay@gmail.com");

        friendRepository.removeFriendByEmail(user, friend.getEmail());
        PreparedStatement selectQuery = connection.prepareStatement("select * from Friend where user_id = ? and friend_id=?");
        selectQuery.setString(1, String.valueOf(user.getUuid()));
        selectQuery.setString(2, String.valueOf(friend.getUuid()));
        ResultSet result = selectQuery.executeQuery();
        Assertions.assertFalse(result.next());
    }

    @Test
    @Order(4)
    public void testRemoveFriendByContact() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        User friend = userRepository.getByEmail("prit@gmail.com");

        friendRepository.removeFriendByContact(user, friend.getContact());
        PreparedStatement selectQuery = connection.prepareStatement("select * from Friend where user_id = ? and friend_id=?");
        selectQuery.setString(1, String.valueOf(user.getUuid()));
        selectQuery.setString(2, String.valueOf(friend.getUuid()));
        ResultSet result = selectQuery.executeQuery();
        Assertions.assertFalse(result.next());
    }
}
