package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.activity.ActivityHelper;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Constants;
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
public class GroupService implements GroupRepository {

    @Autowired
    GroupDataManipulation dataManipulation;

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Override
    public void createGroup(User user,Group group) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_GROUP);
        preparedStatement.setString(1, group.getGroupId());
        preparedStatement.setString(2, group.getGroupName());
        preparedStatement.setString(3, group.getGroupDesc());
        int count = preparedStatement.executeUpdate();
        if(count>0) {
            System.out.println("Group " + group.getGroupName() + " inserted to the database");
         	ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.createGroup,group.getGroupName()));
            
        }
    }


    
    @Override
    public void joinGroup(User user, String inputId) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Groups WHERE group_id ='"+inputId+"'");
        if(DtoUtils.getCountOfRecords(rs) == 1){
            if(!dataManipulation.tableExist("GroupMembers"))
                dataManipulation.createGroupMembersTable();
            stmt.executeUpdate("INSERT INTO GroupMembers VALUES ('"+ inputId + "','" + user.getUuid() +"')");
          	ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.joinGroup,inputId));
            System.out.println("Joined successfully.");
        }else{
            System.out.println("Group does not exist. Please try again");
        }
    }

    @Override
    public void leaveGroup(User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM GroupMembers WHERE uuid ='" + user.getUuid() + "'");
        List<String> ids = DtoUtils.getIdFromResultSet(rs);
        List<String> joinedGroups = new ArrayList<>();

        for (String id : ids) {
            rs = stmt.executeQuery("SELECT group_name FROM Groups WHERE group_id ='" + id + "'");
            while (rs.next())
                joinedGroups.add(rs.getString("group_name"));
        }

        if (joinedGroups.size() == 0) {
            System.out.println("Unfortunately you are not part of any group!!");
        } else {
            System.out.println("Group that you are currently part of: ");
            System.out.println(Arrays.toString(joinedGroups.toArray()));
            System.out.println("Enter the name of group you want to leave: ");

            Scanner sc = new Scanner(System.in);
            String deleteGroup = sc.nextLine();

            if (joinedGroups.contains(deleteGroup)) {
                stmt.executeUpdate("DELETE FROM GroupMembers WHERE group_id ='" + ids.get(joinedGroups.indexOf(deleteGroup)) + "' AND uuid = '" + user.getUuid() + "'");
                ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.leaveGroup,deleteGroup));
                System.out.println("Left from the group " + deleteGroup);
            } else {
                System.out.println("Invalid group name. Please try again");
            }
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

            if(count == 1){
                System.out.println("Are you sure you want to delete group " + groupName + " ?[Y/N]: ");
                Scanner sc = new Scanner(System.in);
                String choice = sc.nextLine();
                if(choice.equalsIgnoreCase("Y")){
                    stmt.executeUpdate("DELETE FROM Groups WHERE group_name ='" + groupName + "'");
                    ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.deleteGroup,groupName));
                    System.out.println("Group " + groupName + " deleted successfully.");
                }else {
                    System.out.println("No changes made");
                }
            }
            else
                System.out.println("You cannot delete this group as you are not a member of it.");
        }else {
            System.out.println("Group not found. Please try again");
        }
    }
}
