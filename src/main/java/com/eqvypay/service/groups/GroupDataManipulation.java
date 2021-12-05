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
    public boolean tableExist(String tableName) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        boolean tableExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tableExists = true;
                    break;
                }
            }
        }
        return tableExists;
    }

    @Override
    public List<Group> getAllGroups() throws Exception {
        List<Group> groups = new ArrayList<>();
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("Select group_id, group_name from Groups");
        while(rs.next()) {
            Group group = new Group();

            group.setGroupId(rs.getString("group_id"));
            group.setGroupName(rs.getString("group_name"));

            groups.add(group);
        }
        return groups;
    }
    
    public List<String> getMembersOfGroup(String groupId) throws Exception {
    	List<String> members = new ArrayList<String>();
    	Connection connection = dcms.getConnection(Environment.DEV);
    	Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from GroupMembers where group_id = '"+groupId+"'");
        while(rs.next()) {
        	members.add(rs.getString("uuid"));
        }
        return members;
    }

    @Override
    public ArrayList<Group> getAllJoinedGroups(User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Groups INNER JOIN GroupMembers on Groups.group_id = GroupMembers.group_id where uuid = '" + user.getUuid().toString() + "'";
        ResultSet resultSet = statement.executeQuery(query);
        return DtoUtils.getGroupsFromResultSet(resultSet);
    }

    @Override
    public List<String> getFriendsGroupIds(User user) throws Exception {

        List<String> friends_group_Id = new ArrayList<>();

        List<String> friendIds = new ArrayList<>();
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT friend_id from Friend WHERE user_id = '"+user.getUuid()+"'");

        while(rs.next()) {
            friendIds.add(rs.getString("friend_id"));
        }
        for(String id: friendIds){
            rs = stmt.executeQuery("SELECT group_id FROM GroupMembers WHERE uuid ='" + id + "'");
            while (rs.next()){
                friends_group_Id.add(rs.getString("group_id"));
            }
        }
        return friends_group_Id;
    }
}
