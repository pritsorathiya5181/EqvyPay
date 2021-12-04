package com.eqvypay.Web;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ExpenseRepository;
import com.eqvypay.Service.FriendRepository;
import com.eqvypay.util.constants.Constants;
import com.eqvypay.util.constants.enums.ExpenseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class ManageExpenseOption {

    public void expenseOptions(User user, ExpenseRepository expenseRepository, FriendRepository friendRepository) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------");
            System.out.println("\tManage Expenses");
            System.out.println("----------------------------\n");
            System.out.println("[1] Add expense");
            System.out.println("[2] Settle expense");
            System.out.println("[3] Exit");

            int option = sc.nextInt();

            if (option == 3) {
                break;
            }

            if (option == 1) {
                System.out.println("[1] Add in the group");
                System.out.println("[2] Add to the friend");
                System.out.println("[3] Exit");

                int payOption = sc.nextInt();

                if (payOption == 3) {
                    break;
                }

                if (payOption == 1) {
                    System.out.println("[1] Add in the existing group");
                    System.out.println("[2] Create a new group");
                    System.out.println("Select one option from the above: ");

                    int groupOption = sc.nextInt();
                    System.out.println("group option " + groupOption);
                    if (groupOption == 1) {
                        System.out.println("List of available groups");
                        ArrayList<Group> groups = expenseRepository.getAllJoinedGroups(user);

                        if (groups.size() > 0) {
                            for (int i = 0; i < groups.size(); i++) {
                                System.out.println((i + 1) + ". " + groups.get(i).getGroupName());
                            }

                            System.out.println("Enter a group name: ");
                            sc.nextLine();
                            String groupNameInput = sc.nextLine();
                            System.out.println("Enter expense description: ");
                            String expenseDesc = sc.nextLine();
                            System.out.println("Enter amount");
                            float expenseAmt = sc.nextFloat();
                            System.out.println("Enter currency type");
                            sc.nextLine();
                            String currencyType = sc.nextLine();

                            for (Group group : groups) {
                                if (group.getGroupName().equals(groupNameInput)) {
                                    Expense newExpense = new Expense();
                                    newExpense.setGroupId(group.getGroupId());
                                    newExpense.setExpenseDesc(expenseDesc);
                                    newExpense.setExpenseAmt(expenseAmt);
                                    newExpense.setCurrencyType(currencyType);
                                    newExpense.setExpenseType(ExpenseType.GROUP);
                                    newExpense.setTargetUserId(user.getUuid().toString());

                                    boolean isTableExists = expenseRepository.tableExist("Expenses");

                                    if (!isTableExists) {
                                        expenseRepository.createTable();
                                    }
                                    Expense expense = expenseRepository.save(newExpense);
                                    System.out.println("Expense target user is " + expense.getTargetUserId());
                                    System.out.println("Expense " + expense.getExpenseAmt());

                                    System.out.println("[1] Split equally");
                                    System.out.println("[2] Split unequally");
                                    int divideType = sc.nextInt();

                                    if (divideType == 1) {
                                        List<Expense> expenses = new ArrayList<Expense>();

                                        ArrayList<Group> members = expenseRepository.getAllJoinedGroups(user);
                                        List<String> groupMembers = new ArrayList<>(Arrays.asList(expense.getTargetUserId(), "#user3", "#user4", "#user1"));
                                        float share = (expense.getExpenseAmt()) / ((groupMembers.size() - 1));
                                        System.out.println("PerShare " + share);
                                        for (String member : groupMembers) {
                                            if (!member.equalsIgnoreCase(expense.getTargetUserId())) {
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
                                            }
                                        }
                                        expenseRepository.saveAll(expenses);
                                        System.out.println("option 1 select");
                                    } else if (divideType == 2) {
                                        List<Expense> expenses = new ArrayList<Expense>();
                                        List<String> groupMembers = new ArrayList<>(Arrays.asList(expense.getTargetUserId(), "#user3", "#user4", "#user1"));
                                        for (String member : groupMembers) {
                                            if (!member.equalsIgnoreCase(expense.getTargetUserId())) {
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
                                                expenses.add(memberExpense);
                                            }
                                        }
                                        expenseRepository.saveAll(expenses);
                                        System.out.println("option 2 select");
                                    }
                                    System.out.println("Succeed! expenses added");
                                }
                            }
                            System.out.println("Failed! Invalid entry! please enter valid group");
                        } else {
                            System.out.println("You're not join in any group");
                        }
                    }

                } else if (payOption == 2) {
                    List<Expense> friendExpenseList = new ArrayList<>();
                    String groupNameInput = sc.nextLine();
                    System.out.println("Enter expense description: ");
                    String expenseDesc = sc.nextLine();
                    System.out.println("Enter amount");
                    float expenseAmt = sc.nextFloat();
                    System.out.println("Enter currency type");
                    sc.nextLine();
                    String currencyType = sc.nextLine();
                    System.out.println("Friend option selected");

                    List<User> friends = expenseRepository.findAllFriends(user.getUuid().toString());
                    int count = 1;
                    System.out.printf("%s%n", "Your added friends list is below");
                    System.out.format("%-15s%-15s%-15s%n", "sr.no", "name", "email");
                    for (User friend : friends) {
                        System.out.format("%-15s%-15s%-15s%n", count, friend.getName(), friend.getEmail());
                    }
                    System.out.println("Enter friend index numbers with spaces \n e.g. 1 2 (for friend 1 and 2)");
                    String requestedFriends[] = sc.next().split(" ");
                    List<Integer> selectedFriends = Arrays.stream(requestedFriends).map(p -> Integer.valueOf(p) - 1).collect(Collectors.toList());
                    List<User> selectedFriendIds = selectedFriends.stream().map(p -> friends.get(p)).collect(Collectors.toList());

                    System.out.println("1. Split Equally");
                    System.out.println("2. Split Unequally");
                    int choice = sc.nextInt();
                    if (choice == 1) {
                        float share = expenseAmt / selectedFriendIds.size();
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
                        expenseRepository.saveAll(friendExpenseList);
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
                        expenseRepository.saveAll(friendExpenseList);
                    }
                }
            } else if (option == 2) {
                System.out.println("Your outstandings are");
                List<Expense> expenses = expenseRepository.getExpensesByUserId(user.getUuid().toString());
                for (Expense expense : expenses) {
                    Currency currency = Currency.getInstance(expense.getCurrencyType());
                    if (expense.getSourceUserId().equals(user.getUuid().toString())) {
                        System.out.format(Constants.settleSource, expense.getTargetUserId(), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()));
                    } else {
                        System.out.format(Constants.settleTarget, expense.getSourceUserId(), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()));
                    }
                    System.out.println();
                }
                if (expenses.size() > 0) {
                    System.out.println("Enter expense to settle, for multiple settlements, enter by spaces");
                    sc.nextLine();
                    String settlementString = sc.nextLine();
                    String[] settlements = settlementString.split(" ");
                    List<Integer> settlementIndexes = Arrays.stream(settlements).map(p -> Integer.valueOf(p) - 1).collect(Collectors.toList());
                    for (int i = 0; i < settlementIndexes.size(); i++) {
                        Expense expenseToBeSettled = expenses.get(i);
                        Currency currency = Currency.getInstance(expenseToBeSettled.getCurrencyType());
                        boolean settled = expenseRepository.settleExpense(expenseToBeSettled);
                        if (settled) {
                            System.out.println("Expense of :" + expenseToBeSettled.getExpenseAmt() + " settled!");
                        }
                    }
                } else {
                    System.out.println("You don't have any expense to settle");
                }
            }
        }
    }
}
