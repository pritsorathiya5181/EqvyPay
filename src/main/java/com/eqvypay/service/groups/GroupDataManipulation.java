package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDataManipulation implements IGroupDataManipulation {

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Override
    public void createTable() throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE TABLE Groups"
                + " ( group_id varchar(255)"
                + ",group_name varchar(255)"
                + ",group_desc varchar(255)  );");
        System.out.println("Groups table created");

    }

    @Override
    public void createGroupMembersTable() throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE TABLE GroupMembers"
                + " ( group_id varchar(255)"
                + ",uuid varchar(255)  );");
        System.out.println("Groups table created");

    }
}
