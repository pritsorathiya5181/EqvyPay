package com.eqvypay.web;

import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.groups.GroupDataManipulation;
import com.eqvypay.service.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class ManageGroupOption {

    @Autowired
    GroupDataManipulation dataManipulation;

    public void groupOptions(User user, GroupRepository groupRepository) throws Exception{

        Scanner sc = new Scanner(System.in);
        int choice;

        while(true){
            System.out.println("----------------------------");
            System.out.println("\tManage Group");
            System.out.println("----------------------------\n");
            System.out.println("[1] Create group");
            System.out.println("[2] Join group");
            System.out.println("[3] Leave group");
            System.out.println("[4] Delete group");
            System.out.println("[5] Exit");
            System.out.println("Select an option: ");

            choice = sc.nextInt();

            if(choice == 5) {
            	break;
            }
            
            switch (choice){
                case 1:
                    Group group = new Group();
                    System.out.println("Enter group name");
                    sc.nextLine();
                    String groupName = sc.nextLine();
                    group.setGroupName(groupName);
                    System.out.println("Enter group description");
                    group.setGroupDesc(sc.nextLine());
                    try{
                        if (!dataManipulation.tableExist("Groups")) {
                            dataManipulation.createTable();
                        }
                        //INSERT ROW TO GROUPS TABLE
                        groupRepository.createGroup(group);
                        groupRepository.joinGroup(user, group.getGroupId());

                    }catch (Exception e){
                        System.out.println("Error: " + e.toString());
                    }
                    break;

                case 2:
                    try {
                        List<String> groupIds = dataManipulation.getFriendsGroupIds(user);
                        List<Group> all_groups = dataManipulation.getAllGroups();

                        System.out.println("List of groups that your friends are member of:");
                        for(Group each_group: all_groups){
                            if(groupIds.contains(each_group.getGroupId())){
                                System.out.println("Group ID: " + each_group.getGroupId() + "\tGroup Name: " + each_group.getGroupName());
                            }
                        }

                        System.out.println("Enter group ID to join: ");
                        String groupId = sc.next().toUpperCase();
                        if(groupIds.contains(groupId)){
                            groupRepository.joinGroup(user, groupId);
                        }else{
                            System.out.println("Enter group id from list given only. Please try again.");
                        }
                    }catch (Exception e){
                        System.out.println("Exception occurred: " + e.toString());
                    }

                    break;

                case 3:
                    try {
                        groupRepository.leaveGroup(user);
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;

                case 4:
                    System.out.println("Enter group name");
                    String group_name = sc.next();
                    try {
                        groupRepository.deleteGroup(group_name, user);
                    }catch (Exception e){
                        System.out.println("Error: " + e.toString());
                    }

                    break;

                default:
                    System.out.println("Invalid choice. Please try again");
                    break;
            }


        }


    }
}
