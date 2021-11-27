package com.eqvypay.Web;

import com.eqvypay.Persistence.Group;
import com.eqvypay.Service.GroupRepository;

import java.util.Scanner;

public class ManageGroupOption {

    public void groupOptions(GroupRepository groupRepository){

        Scanner sc = new Scanner(System.in);
        int choice;

        while(true){
            System.out.println("----------------------------");
            System.out.println("\tManage Group");
            System.out.println("----------------------------\n");
            System.out.println("1. Create group");
            System.out.println("2. Join group");
            System.out.println("3. Leave group");
            System.out.println("2. Delete group");
            System.out.println("Select your choice: ");

            choice = sc.nextInt();

            switch (choice){
                case 1:
                    Group group = new Group();

                    System.out.println("Enter group name");
                    group.setGroupName(sc.next());
                    System.out.println("Enter group description");
                    group.setGroupDesc(sc.next());
                    try{
                        if(groupRepository.tableExist("Groups")){
                            System.out.println("Table Groups exists, inserting into it");
                        }else {
                            groupRepository.createGroupTable();
                        }
                        //INSERT ROW TO GROUPS TABLE
                        groupRepository.save(group);
                    }catch (Exception e){
                        System.out.println("Error: " + e.toString());
                    }



                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Invalid choice. Please try again");
                    break;
            }


        }


    }
}
