package com.eqvypay.service.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.User;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.DatabaseConstants;

@Service
public class UserService implements UserRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'"+"AND password="+"'"+password+"'");
        return dtoUtils.getUserFromResultSet(resultSet);
    }

    @Override
    public User getByEmail(String email) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'");
        return dtoUtils.getUserFromResultSet(resultSet);
    }
    @Override
    public User getByUuid(UUID uuid) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE uuid = '"+uuid+"'");
        return dtoUtils.getUserFromResultSet(resultSet);
    }

    @Override
    public List<User> findAllFriends(String userId) throws Exception {
        if(dtoUtils.tableExist(dcms, "Friend")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Friend inner join Users on Friend.friend_id = Users.uuid where Friend.user_id ='" + userId + "'");
            return dtoUtils.getAllFriendsFromResultSet(rs);
        } else {
            System.out.println("Your friend list is empty! Please add a friend first.");
            return null;
        }
    }
}
