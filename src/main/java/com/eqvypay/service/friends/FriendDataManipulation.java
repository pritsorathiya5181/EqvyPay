package com.eqvypay.service.friends;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Statement;

@Service
public class FriendDataManipulation implements IFriendDataManipulation {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public void createTable() throws Exception {
        String query = "CREATE TABLE Friend"
                + " ( user_id varchar(255) "
                + " ,friend_id varchar(255) );";
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement s = connection.createStatement();
        String tableName = "Friend";

        if (!dtoUtils.tableExist(dcms, tableName)) {
            s.executeUpdate(query);
        }
    }
}