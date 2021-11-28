package com.eqvypay.Service;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
    public void createGroupMembersTable() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();

        stmt.executeUpdate("CREATE TABLE GroupMembers"
                + " ( group_id varchar(255)"
                + ",uuid varchar(255)  );");
        System.out.println("Groups table created");

    }

    @Override
    public void removeGroupMember(User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM GroupMembers WHERE uuid ='"+user.getUuid()+"'");
        // rs is, group_id column from GroupMembers
        List<String> ids = DtoUtils.getIdFromResultSet(rs);
        List<String> joinedGroups = new ArrayList<>();

        for(String id: ids){
            rs = stmt.executeQuery("SELECT group_name FROM Groups WHERE group_id ='"+id+"'");
            while (rs.next())
                joinedGroups.add(rs.getString("group_name"));
        }

        System.out.println("Group that you are currently part of: ");
        System.out.println(Arrays.toString(joinedGroups.toArray()));
        System.out.println("Enter the name of group you want to leave: ");

        Scanner sc = new Scanner(System.in);
        String deleteGroup = sc.nextLine();

        if(joinedGroups.contains(deleteGroup)){
            stmt.executeUpdate("DELETE FROM GroupMembers WHERE group_id ='"+ ids.get(joinedGroups.indexOf(deleteGroup)) +"' AND uuid = '" + user.getUuid() + "'");
            System.out.println("Left from the group " + deleteGroup);
        }else{
            System.out.println("Invalid group name. Please try again");
        }

    }

    @Override
    public boolean tableExist(String tableName) throws Exception {
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
    public void deleteGroup(String groupName, User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT group_id FROM Groups WHERE group_name ='" + groupName + "'");
        String groupId = "";
        while (rs.next()){
            groupId = rs.getString("group_id");
        }
        if(groupId != ""){
            String query = "SELECT * FROM GroupMembers WHERE group_id='" + groupId + "' AND uuid='" + user.getUuid() + "'";
            rs = stmt.executeQuery(query);
            int count = DtoUtils.getCountOfRecords(rs);
            //check if group_id, and user_id exits in GroupMembers
            if(count == 1)
                stmt.executeUpdate("DELETE FROM Groups WHERE group_name ='" + groupName + "'");
            else
                System.out.println("You cannot delete this group as you are not a member of it.");
        }else {
            System.out.println("Group not found. Please try again");
        }


//        Statement stmt = connection.createStatement();
//
//        System.out.println("Group " + groupName + " deleted successfully.");
    }

    @Override
    public void addGroupMember(User user, String inputId) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Groups WHERE group_id ='"+inputId+"'");
        if(DtoUtils.getCountOfRecords(rs) == 1){
            if(!tableExist("GroupMembers"))
                createGroupMembersTable();
            stmt.executeUpdate("INSERT INTO GroupMembers VALUES ('"+ inputId + "','" + user.getUuid() +"')");
            System.out.println("Joined successfully.");
        }else{
            System.out.println("Group does not exist. Please try again");
        }
    }
}
