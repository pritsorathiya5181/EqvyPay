package com.eqvypay.service;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.groups.GroupDataManipulation;
import com.eqvypay.service.groups.GroupRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.constants.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.util.List;

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
        Group testGroup = new Group();
        testGroup.setGroupId("TEST_CREATE_GROUP_ID");
        testGroup.setGroupName("TEST_CREATE_GROUP_NAME");
        testGroup.setGroupDesc("TEST_CREATE_GROUP_DESC");

        groupRepository.createGroup(testGroup);
        List<Group> allGroups = groupRepository.getAllGroups();

        boolean shouldBeTrue = false;
        for (Group eachGroup : allGroups) {
            System.out.println(eachGroup.getGroupId());
            if (eachGroup.getGroupId().equals(testGroup.getGroupId())) {
                shouldBeTrue = true;
                break;
            }
        }
        Assertions.assertTrue(shouldBeTrue);
    }

    @Test
    @Order(2)
    public void testJoinGroup() throws Exception {

        User testUser = new User();
        Group testGroup = new Group();
//        testGroup.setGroupId("TEST_JOIN_GROUP_ID");
        testGroup.setGroupName("TEST_JOIN_GROUP_NAME");
        testGroup.setGroupDesc("TEST_JOIN_GROUP_DESC");

        groupRepository.createGroup(testGroup);
        groupRepository.joinGroup(testUser, testGroup.getGroupId());

        List<String> allMembersId = groupRepository.getMembersOfGroup(testGroup.getGroupId());

        boolean shouldBeTrue = false;

        for(String uuid: allMembersId){
            System.out.println(uuid);
            if(uuid.equals(testUser.getUuid().toString())){
                shouldBeTrue = true;
                break;
            }
        }
        Assertions.assertTrue(shouldBeTrue);
    }

    @Test
    @Order(3)
    public void testLeaveGroup() {
        try {
            User testUser = new User();
            Group testGroup = new Group();

            testGroup.setGroupId("TEST_LEAVE_GROUP_ID");
            testGroup.setGroupName("TEST_LEAVE_GROUP_NAME");
            testGroup.setGroupDesc("TEST_LEAVE_GROUP_DESC");

            groupRepository.createGroup(testGroup);
            groupRepository.joinGroup(testUser, testGroup.getGroupId());
            groupRepository.leaveGroup(testUser, testGroup.getGroupName());

            boolean shouldBeFalse = groupRepository.getMembersOfGroup(testGroup.getGroupId()).contains(testUser.getUuid());

            Assertions.assertFalse(shouldBeFalse);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(4)
    public void testDeleteGroup() throws Exception {
        User testUser = new User();
        Group testGroup = new Group();

        testGroup.setGroupId("TEST_DELETE_GROUP_ID");
        testGroup.setGroupName("TEST_DELETE_GROUP_NAME");
        testGroup.setGroupDesc("TEST_DELETE_GROUP_DESC");

        groupRepository.createGroup(testGroup);
        groupRepository.joinGroup(testUser, testGroup.getGroupId());

        groupRepository.deleteGroup(testGroup.getGroupName(), testUser);

        boolean shouldRemainTrue = true;

        for(Group eachGroup: groupRepository.getAllGroups()) {
            if(eachGroup.getGroupId() != null && eachGroup.getGroupId().equals(testGroup.getGroupId())) {
                shouldRemainTrue = false;
            }
        }

        Assertions.assertTrue(shouldRemainTrue);
    }
}
