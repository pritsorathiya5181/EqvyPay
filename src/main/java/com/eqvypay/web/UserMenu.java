package com.eqvypay.web;

import com.eqvypay.persistence.User;
import com.eqvypay.service.expense.ExpenseRepository;
import com.eqvypay.service.friends.FriendRepository;
import com.eqvypay.service.groups.GroupRepository;
import com.eqvypay.service.moneymanager.MoneyManagerRepository;
import com.eqvypay.service.profile.ProfileRepository;

import java.util.Scanner;

import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMenu {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MoneyManagerRepository moneyManagerRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    AddFriendOption addFriendOption;

    @Autowired
    RemoveFriendOption removeFriendOption;

    @Autowired
    ManageExpenseOption manageExpenseOption;

    @Autowired
    ManageGroupOption manageGroupOption;

    @Autowired
    MoneyManagerOption moneyManagerOption;

    @Autowired
    UpdateProfileOption profileOptions;

    public int userNewOptions(User user) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("------------------------------");
            System.out.println("\tEqvypay Menu");
            System.out.println("------------------------------");
            System.out.println("[1] Add a friend");
            System.out.println("[2] Remove a friend");
            System.out.println("[3] Manage group");
            System.out.println("[4] Manage expense");
            System.out.println("[5] Activity");
            System.out.println("[6] Profile details");
            System.out.println("[7] Money manager");
            System.out.println("[8] Logout");
            System.out.print("Select an option: ");

            String option = sc.next();
            if (option.equals("8")) {
                return 8;
            } else {
                switch (option) {
                    case "1":
                        System.out.println("Add friend option selected");
                        addFriendOption.friendOptions(user, friendRepository);
                        break;
                    case "2":
                        System.out.println("Remove friend option selected");
                        removeFriendOption.friendOptions(user, friendRepository);
                        break;
                    case "3":
                        System.out.println("Manage Groups");
                        manageGroupOption.groupOptions(user, groupRepository);
                        break;
                    case "4":
                        System.out.println("Manage option selected");
                        manageExpenseOption.options(user, expenseRepository);
                        break;
                    case "5":
                        System.out.println("Integrate activity module here");
                        break;
                    case "6":
                        profileOptions.updateProfileOption(user, profileRepository);
                        break;
                    case "7":
                        System.out.println("Money manager option selected");
                        moneyManagerOption.handleOption(user, moneyManagerRepository);
                    default:
                        System.out.println("Invalid choice. Please try again");
                }
            }

        }
    }
}
