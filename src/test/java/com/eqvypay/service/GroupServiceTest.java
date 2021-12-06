package com.eqvypay.service;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.groups.GroupDataManipulation;
import com.eqvypay.service.groups.GroupRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.constants.Environment;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootTest
public class GroupServiceTest {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupDataManipulation groupDataManipulation;

    @Autowired
    UserDataManipulation userDataManipulation;

    @Autowired
    GroupRepository groupRepository;

    private static Connection connection;

    @Test
    @Order(1)
    public void testCreateGroup() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        Group group = new Group();
        group.setGroupId("groupid");
        group.setGroupName("group1");
        group.setGroupDesc("new group");

        groupRepository.createGroup(group);
        PreparedStatement selectQuery = connection.prepareStatement("select * from Groups where group_id = ?");
        selectQuery.setString(1, String.valueOf(group.getGroupId()));
        ResultSet result = selectQuery.executeQuery();
        Assertions.assertTrue(result.next());
    }

    @Test
    @Order(2)
    public void testJoinGroup() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        String groupId = "groupid";

        groupRepository.joinGroup(user, groupId);
        PreparedStatement selectQuery = connection.prepareStatement("select * from GroupMembers where group_id = ?");
        selectQuery.setString(1, groupId);
        ResultSet result = selectQuery.executeQuery();
        while(result.next()){
            Assertions.assertEquals(result.getString("uuid"), user.getUuid().toString());
        }
    }

    @Test
    @Order(3)
    public void testLeaveGroup(){
        //remaining as it needs user input
    }

    @Test
    @Order(4)
    @Ignore
    public void testDeleteGroup() throws Exception {
        //ignored as needs user input
//        connection = dcms.getConnection(Environment.TEST);
//
//        User user = userDataManipulation.getByEmail("hirva@gmail.com");
//        String groupName = "group1";
//        groupRepository.deleteGroup(groupName, user);
//        PreparedStatement selectQuery = connection.prepareStatement("select * from Groups where group_name = ?");
//        selectQuery.setString(1, groupName);
//        ResultSet result = selectQuery.executeQuery();
//        Assertions.assertFalse(result.next());
//
    }
}
