package com.eqvypay.Web;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.GroupRepository;

import java.util.Scanner;

public class ManageGroupOption {

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
                        if (!groupRepository.tableExist("Groups")) {
                            groupRepository.createGroupTable();
                        }
                        //INSERT ROW TO GROUPS TABLE
                        groupRepository.save(group);
                        groupRepository.addGroupMember(user, group.getGroupId());

                    }catch (Exception e){
                        System.out.println("Error: " + e.toString());
                    }
                    break;

                case 2:
                	groupRepository.getAllGroups();
                    System.out.println("Enter group ID to join");
                    try {
                        groupRepository.addGroupMember(user, sc.next().toUpperCase());
                    }catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;

                case 3:
                    try {
                        groupRepository.removeGroupMember(user);
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
