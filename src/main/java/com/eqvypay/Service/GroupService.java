package com.eqvypay.Service;

import com.eqvypay.Persistence.Group;
import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class GroupService implements GroupRepository{

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Override
    public void createGroupTable() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE TABLE Groups"
                + " ( group_id varchar(255)"
                + ",group_name varchar(255)"
                + ",group_desc varchar(255)  );");
        System.out.println("Groups table created");

    }

    @Override
    public boolean tableExist(String tableName) throws Exception {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        Connection connection = dcms.getConnection(Environment.DEV);
        boolean tableExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                System.out.println(tName);
                if (tName != null && tName.equals(tableName)) {
                    tableExists = true;
                    break;
                }
            }
        }
        return tableExists;
    }

    @Override
    public void save(Group group) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_GROUP);
        preparedStatement.setString(1, group.getGroupId());
        preparedStatement.setString(2, group.getGroupName());
        preparedStatement.setString(3, group.getGroupDesc());
        int count = preparedStatement.executeUpdate();
        if(count>0) {
            System.out.println("Group " + group.getGroupName() + "inserted to the database");
        }




    }

    @Override
    public void deleteGroupTable() throws Exception {

    }

    @Override
    public Group getGroupById(String groupId) throws Exception {
        return null;
    }

    @Override
    public void addGroupMember(Group group) throws Exception {

    }
}
