package com.eqvypay.service.groups;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.IGroup;
import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;
import com.eqvypay.service.activity.ActivityHelper;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Constants;
import com.eqvypay.util.constants.DatabaseConstants;
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

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public void createGroup(IUser user, IGroup group) throws Exception {
        if (!dtoUtils.tableExist(dcms, "Groups")) {
            dataManipulation.createTable();
        }

        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_GROUP);
        preparedStatement.setString(1, group.getGroupId());
        preparedStatement.setString(2, group.getGroupName());
        preparedStatement.setString(3, group.getGroupDesc());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Group " + group.getGroupName() + " inserted to the database");
         	ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.createGroup,group.getGroupName()));

        }
    }

    @Override
    public void joinGroup(IUser user, String inputId) throws Exception {
        if (dtoUtils.tableExist(dcms, "Groups")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Groups WHERE group_id ='" + inputId + "'");
            if (dtoUtils.getCountOfRecords(rs) == 1) {
                if (!dtoUtils.tableExist(dcms, "GroupMembers"))
                    dataManipulation.createGroupMembersTable();
                stmt.executeUpdate("INSERT INTO GroupMembers VALUES ('" + inputId + "','" + user.getUuid() + "')");
                System.out.println("Joined successfully.");
            } else {
                System.out.println("Group does not exist. Please try again");
            }
        } else {
            System.out.println("No groups are available.");
        }
    }

    @Override
    public void leaveGroup(IUser user, String groupName) throws Exception {
        if (dtoUtils.tableExist(dcms, "Groups")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GroupMembers WHERE uuid ='" + user.getUuid() + "'");
            List<String> ids = dtoUtils.getIdFromResultSet(rs);
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

                if (joinedGroups.contains(groupName)) {
                    stmt.executeUpdate("DELETE FROM GroupMembers WHERE group_id ='" + ids.get(joinedGroups.indexOf(groupName)) + "' AND uuid = '" + user.getUuid() + "'");
                    System.out.println("Left from the group " + groupName);
                } else {
                    System.out.println("Invalid group name. Please try again");
                }
            }
        } else {
            System.out.println("No groups are available.");
        }
    }

    @Override
    public void deleteGroup(String groupName, IUser user) throws Exception {
        if (dtoUtils.tableExist(dcms, "Groups")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT group_id FROM Groups WHERE group_name ='" + groupName + "'");
            String groupId = "";
            while (resultSet.next()) {
                groupId = resultSet.getString("group_id");
            }
            if (groupId != "") {
                String query = "SELECT * FROM GroupMembers WHERE group_id='" + groupId + "' AND uuid='" + user.getUuid() + "'";
                resultSet = statement.executeQuery(query);
                int count = dtoUtils.getCountOfRecords(resultSet);

                if (count == 1) {
                    statement.executeUpdate("DELETE FROM Groups WHERE group_name ='" + groupName + "'");
                    System.out.println("Group " + groupName + " deleted successfully.");

                } else
                    System.out.println("You cannot delete this group as you are not a member of it.");
            } else {
                System.out.println("Group not found with '" + groupId + "'. Please try again.");
            }
        } else {
            System.out.println("No groups are available.");
        }

    }

    @Override
    public List<IGroup> getAllGroups() throws Exception {
        List<IGroup> groups = new ArrayList<>();
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from Groups");
        while (rs.next()) {
            IGroup group = GroupFactory.getInstance().getGroup();

            group.setGroupId(rs.getString("group_id"));
            group.setGroupName(rs.getString("group_name"));
            group.setGroupDesc(rs.getString("group_desc"));

            groups.add(group);
        }
        return groups;
    }

    @Override
    public List<String> getMembersOfGroup(String groupId) throws Exception {
        if (dtoUtils.tableExist(dcms, "GroupMembers")) {
            List<String> members = new ArrayList<String>();
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from GroupMembers where group_id = '" + groupId + "'");
            while (rs.next()) {
                members.add(rs.getString("uuid"));
            }
            return members;
        } else {
            System.out.println("There are no members in the group, add them first");
            return null;
        }
    }

    @Override
    public ArrayList<IGroup> getAllJoinedGroups(IUser user) throws Exception {
        if (dtoUtils.tableExist(dcms, "GroupMembers")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Groups INNER JOIN GroupMembers on Groups.group_id = GroupMembers.group_id where uuid = '" + user.getUuid().toString() + "'";
            ResultSet resultSet = statement.executeQuery(query);
            return dtoUtils.getGroupsFromResultSet(resultSet);
        }
       else {
            System.out.println("You are not joined in any groups");
            return null;
        }
    }

    @Override
    public List<String> getFriendsGroupIds(IUser user) throws Exception {
        boolean hasFriendTable = dtoUtils.tableExist(dcms, "Friend");
        boolean hasGroupTable = dtoUtils.tableExist(dcms, "GroupMembers");

        if (hasFriendTable && hasGroupTable) {
            List<String> friends_group_Id = new ArrayList<>();

            List<String> friendIds = new ArrayList<>();
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT friend_id from Friend WHERE user_id = '" + user.getUuid() + "'");

            while (rs.next()) {
                friendIds.add(rs.getString("friend_id"));
            }
            for (String id : friendIds) {
                rs = stmt.executeQuery("SELECT group_id FROM GroupMembers WHERE uuid ='" + id + "'");
                while (rs.next()) {
                    friends_group_Id.add(rs.getString("group_id"));
                }
            }
            return friends_group_Id;
        } else {
            System.out.println("No groups are available");
            return null;
        }
    }
}
