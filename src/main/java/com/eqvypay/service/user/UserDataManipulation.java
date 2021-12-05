package com.eqvypay.service.user;

import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Service
public class UserDataManipulation implements IUserDataManipulation {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Override
    public void createTable() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement s = connection.createStatement();
        s.executeUpdate("CREATE TABLE Users"
                + " ( uuid varchar(255) PRIMARY KEY"
                + " ,name varchar(255)"
                + " ,email varchar(255)"
                + " ,contact varchar(255)"
                + " ,password varchar(255)"
                + " ,security_answer varchar(255) );"
        );
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'"+"AND password="+"'"+password+"'");
        return DtoUtils.getUserFromResultSet(resultSet);
    }

    @Override
    public User getByEmail(String email) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE email ="+"'"+email+"'");
        return DtoUtils.getUserFromResultSet(resultSet);
    }
    @Override
    public User getByUuid(UUID uuid) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from Users WHERE uuid = '"+uuid+"'");
        return DtoUtils.getUserFromResultSet(resultSet);
    }
    
    @Override
    public List<User> findAllFriends(String userId) throws Exception {
       Connection connection = dcms.getConnection(Environment.DEV);
       Statement statement = connection.createStatement();
       ResultSet rs = statement.executeQuery("select * from Friend inner join Users on Friend.friend_id = Users.uuid where Friend.user_id ='" + userId + "'");
       return DtoUtils.getAllFriendsFromResultSet(rs);
    }
}
