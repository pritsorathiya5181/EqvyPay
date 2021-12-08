package com.eqvypay.web;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.User;
import com.eqvypay.service.activity.ActivityHelper;
import com.eqvypay.service.expense.ExpenseDataManipulation;
import com.eqvypay.service.groups.GroupDataManipulation;
import com.eqvypay.service.expense.ExpenseRepository;
import com.eqvypay.service.groups.GroupRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Constants;
import com.eqvypay.util.constants.enums.ExpenseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ManageExpenseOption {

    @Autowired
    ExpenseDataManipulation dataManipulation;

    @Autowired
    UserDataManipulation userDataManipulation;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupDataManipulation groupDataManipulation;

    @Autowired
    GroupRepository groupRepository;

    public void options(User user, ExpenseRepository expenseRepository) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------");
            System.out.println("\tManage Expenses");
            System.out.println("----------------------------\n");
            System.out.println("[1] Add expense");
            System.out.println("[2] Settle expense");
            System.out.println("[3] Exit");

            String option = sc.next();

            if (option.equals("3")) {
                break;
            }

            if (option.equals("1")) {
                System.out.println("[1] Add in the group");
                System.out.println("[2] Add to the friend");
                System.out.println("[3] Exit");

                String payOption = sc.next();

                if (payOption.equals("3")) {
                    break;
                }

                if (payOption.equals("1")) {
                    System.out.println("[1] Add in the existing group");
                    System.out.println("[2] Create a new group");
                    System.out.print("Select am option: ");

                    String groupOption = sc.next();
                    System.out.println("group option " + groupOption);
                    if (groupOption.equals("1")) {
                        System.out.println("List of available groups");
                        ArrayList<Group> groups = groupRepository.getAllJoinedGroups(user);

                        if (groups.size() > 0) {
                            for (int i = 0; i < groups.size(); i++) {
                                System.out.println((i + 1) + ". " + groups.get(i).getGroupName());
                            }

                            System.out.println("Enter a group name: ");
                            sc.nextLine();
                            String groupNameInput = sc.nextLine();

                            for (Group group : groups) {
                                if (group.getGroupName().equals(groupNameInput)) {
                                    System.out.println("Enter expense description: ");
                                    String expenseDesc = sc.nextLine();
                                    System.out.println("Enter amount");
                                    float expenseAmt = sc.nextFloat();
                                    System.out.println("Enter currency type");
                                    sc.nextLine();
                                    String currencyType = sc.nextLine();

                                    Expense newExpense = new Expense();
                                    newExpense.setGroupId(group.getGroupId());
                                    newExpense.setExpenseDesc(expenseDesc);
                                    newExpense.setExpenseAmt(expenseAmt);
                                    newExpense.setCurrencyType(currencyType);
                                    newExpense.setExpenseType(ExpenseType.GROUP);
                                    newExpense.setTargetUserId(user.getUuid().toString());

                                    Expense expense = dataManipulation.save(newExpense);
                                    System.out.println("Expense target user is " + expense.getTargetUserId());
                                    System.out.println("Expense " + expense.getExpenseAmt());

                                    System.out.println("[1] Split equally");
                                    System.out.println("[2] Split unequally");
                                    int divideType = sc.nextInt();

                                    if (divideType == 1) {
                                        List<Expense> expenses = new ArrayList<Expense>();

                                        List<String> members = groupRepository.getMembersOfGroup(newExpense.getGroupId());
                                        if (members.size() <= 1) {
                                            System.out.println("There are no members in the group, add them first");
                                            break;
                                        }
                                        float share = expense.getExpenseAmt() / (members.size());

                                        System.out.println("PerShare " + share);
                                        for (String member : members) {
                                            if (!member.equalsIgnoreCase(expense.getTargetUserId())) {
                                            	User groupMember = userRepository.getByUuid(UUID.fromString(member));
                                                Expense memberExpense = new Expense();
                                                memberExpense.setId(UUID.randomUUID().toString());
                                                memberExpense.setCurrencyType(expense.getCurrencyType());
                                                memberExpense.setExpenseAmt(share);
                                                memberExpense.setExpenseDesc(expense.getExpenseDesc());
                                                memberExpense.setExpenseType(expense.getExpenseType());
                                                memberExpense.setGroupId(expense.getGroupId());
                                                memberExpense.setSourceUserId(member);
                                                memberExpense.setTargetUserId(user.getUuid().toString());
                                                expenses.add(memberExpense);
                                                ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.settleTarget,groupMember.getName(),String.valueOf(expense.getExpenseAmt()).concat(expense.getCurrencyType())));
                                                ActivityHelper.addActivity(member,String.format(Constants.settleSource,(user.getName()),String.valueOf(expense.getExpenseAmt()).concat(expense.getCurrencyType())));
                                            }
                                        }
                                        dataManipulation.saveAll(expenses);
                                        System.out.println("option 1 select");
                                    } else if (divideType == 2) {
                                        List<Expense> expenses = new ArrayList<Expense>();
//                                      List<String> groupMembers = new ArrayList<>(Arrays.asList(expense.getTargetUserId(), "#user3", "#user4", "#user1"));
                                        List<String> groupMembers = groupRepository.getMembersOfGroup(newExpense.getGroupId());
                                        for (String member : groupMembers) {
                                            if (!member.equalsIgnoreCase(expense.getTargetUserId())) {
                                               	User groupMember = userRepository.getByUuid(UUID.fromString(member));
                                                Expense memberExpense = new Expense();
                                                memberExpense.setId(UUID.randomUUID().toString());
                                                memberExpense.setCurrencyType(expense.getCurrencyType());
                                                System.out.println("Please add share of user: " + member);
                                                float share = sc.nextFloat();
                                                memberExpense.setExpenseAmt(share);
                                                memberExpense.setExpenseDesc(expense.getExpenseDesc());
                                                memberExpense.setExpenseType(expense.getExpenseType());
                                                memberExpense.setGroupId(expense.getGroupId());
                                                memberExpense.setSourceUserId(member);
                                                memberExpense.setTargetUserId(user.getUuid().toString());
                                                ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.settleTarget,groupMember.getName(),String.valueOf(expense.getExpenseAmt()).concat(expense.getCurrencyType())));
                                                ActivityHelper.addActivity(member,String.format(Constants.settleSource,user.getName(),String.valueOf(expense.getExpenseAmt()).concat(expense.getCurrencyType())));
                                                expenses.add(memberExpense);
                                            }
                                        }
                                        dataManipulation.saveAll(expenses);
                                        System.out.println("option 2 select");
                                    }
                                    System.out.println("Succeed! Expenses added");
                                } else {
                                    System.out.println("Failed! Invalid entry! please enter valid group name");
                                }
                            }
                        } else {
                            System.out.println("You're not join in any group");
                        }
                    } else if (groupOption.equals("2")) {
                        Group group = new Group();
                        System.out.println("Enter group name");
                        sc.nextLine();
                        String groupName = sc.nextLine();
                        group.setGroupName(groupName);
                        System.out.println("Enter group description");
                        group.setGroupDesc(sc.nextLine());
                        try {
                            groupRepository.createGroup(user, group);
                            groupRepository.joinGroup(user, group.getGroupId());
                            ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.joinGroup,groupName));

                        } catch (Exception e) {
                            System.out.println("Error: " + e.toString());
                        }
                        break;
                    }

                } else if (payOption.equals("2")) {
                    List<Expense> friendExpenseList = new ArrayList<>();
                    List<User> friends = userRepository.findAllFriends(user.getUuid().toString());
                    int count = 1;

                    if (friends.size() > 0) {
                        System.out.println("Enter expense description: ");
                        String expenseDesc = sc.nextLine();
                        sc.nextLine();
                        System.out.println("Enter amount");
                        float expenseAmt = sc.nextFloat();
                        System.out.println("Enter currency type");
                        sc.nextLine();
                        String currencyType = sc.nextLine();
                        System.out.println("Friend option selected");

                        System.out.printf("%s%n", "List of friends");
                        System.out.format("%-15s%-15s%-15s%n", "sr.no", "name", "email");
                        for (User friend : friends) {
                            System.out.format("%-15s%-15s%-15s%n", count, friend.getName(), friend.getEmail());
                            count++;
                        }
                        System.out.println("Enter friend index numbers with spaces \n e.g. 1 2 (for friend 1 and 2)");
                        String requestedFriends[] = sc.next().split(" ");

                        List<Integer> selectedFriends = Arrays.stream(requestedFriends).map(p -> Integer.valueOf(p) - 1).collect(Collectors.toList());
                        List<User> selectedFriendIds = selectedFriends.stream().map(p -> friends.get(p)).collect(Collectors.toList());

                        System.out.println("1. Split Equally");
                        System.out.println("2. Split Unequally");
                        int choice = sc.nextInt();

                        if (choice == 1) {
                            float share = expenseAmt / (selectedFriendIds.size() + 1);
                            for (User friend : selectedFriendIds) {
                                Expense expense = new Expense();
                                expense.setCurrencyType(currencyType);
                                expense.setExpenseAmt(share);
                                expense.setExpenseDesc(expenseDesc);
                                expense.setExpenseType(ExpenseType.INDIVIDUAL);
                                expense.setId(UUID.randomUUID().toString());
                                expense.setSourceUserId(friend.getUuid().toString());
                                expense.setTargetUserId(user.getUuid().toString());
                                friendExpenseList.add(expense);
                            }
                            dataManipulation.saveAll(friendExpenseList);
                        } else {
                            for (User friend : selectedFriendIds) {
                                Expense expense = new Expense();
                                expense.setCurrencyType(currencyType);
                                System.out.println("Enter share for friend :" + friend.getEmail());
                                float share = sc.nextFloat();
                                expense.setExpenseAmt(share);
                                expense.setExpenseDesc(expenseDesc);
                                expense.setExpenseType(ExpenseType.INDIVIDUAL);
                                expense.setId(UUID.randomUUID().toString());
                                expense.setSourceUserId(friend.getUuid().toString());
                                expense.setTargetUserId(user.getUuid().toString());
                                friendExpenseList.add(expense);
                            }
                            dataManipulation.saveAll(friendExpenseList);
                        }
                    } else {
                        System.out.println("Your friend list is empty! Please add a friend first.");
                    }
                }
            } else if (option.equals("2")) {
                System.out.println("Your outstandings are:");
                List<Expense> expenses = expenseRepository.getExpensesByUserId(user.getUuid().toString());

                if (expenses != null && expenses.size() > 0) {
                    for (Expense expense : expenses) {
                        Currency currency = Currency.getInstance(expense.getCurrencyType().toUpperCase());
                        if (expense.getSourceUserId().equals(user.getUuid().toString())) {
                            User targetUser = userRepository.getByUuid(UUID.fromString(expense.getTargetUserId()));
                            System.out.format(Constants.settleSource, targetUser.getName().concat("( " + targetUser.getEmail() + " )"), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()));
                        } else {
                            User sourceUser = userRepository.getByUuid(UUID.fromString(expense.getSourceUserId()));
                            System.out.format(Constants.settleTarget, sourceUser.getName().concat("( " + sourceUser.getEmail() + " )"), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()));
                        }
                        System.out.println();
                    }

                    System.out.println("Enter expense to settle, for multiple settlements, enter by spaces \n e.g. 10 20.5 (for first and second expenses)");
                    sc.nextLine();
                    String settlementString = sc.nextLine();
                    String[] settlements = settlementString.trim().split(" ");
                    List<Float> settlementIndexes = Arrays.stream(settlements).map(p -> Float.valueOf(p) - 1).collect(Collectors.toList());
                    for (int i = 0; i < settlementIndexes.size(); i++) {
                        Expense expenseToBeSettled = expenses.get(i);
                        User targetUser = userRepository.getByUuid(UUID.fromString( expenseToBeSettled.getTargetUserId()));
                        Currency currency = Currency.getInstance(expenseToBeSettled.getCurrencyType().toUpperCase());
                        boolean settled = expenseRepository.settleExpense(expenseToBeSettled);
                        if (settled) {
                            System.out.println("Expense of :" + expenseToBeSettled.getExpenseAmt() + " settled!");
                            ActivityHelper.addActivity(user.getUuid().toString(), String.format(Constants.expenseSettlement,user.getName(),targetUser.getName(),expenseToBeSettled.getExpenseAmt()));
                        }
                    }
                } else {
                    System.out.println("You don't have any expense to settle");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }


        }
    }
}
