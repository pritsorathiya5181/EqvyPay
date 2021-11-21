package com.eqvypay.Web;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"com.eqvypay.Service"})
public class ManageExpenseOption {
    public boolean expenseOptions(ExpenseRepository expenseRepository) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------");
            System.out.println("\tManage Expenses");
            System.out.println("----------------------------\n");
            System.out.println("1. Add expense");
            System.out.println("2. Settle expense");
            System.out.println("Select one option from the above: ");

            int option = sc.nextInt();

            if(option == 1) {
                System.out.println("1. Add in the group");
                System.out.println("2. Add to the friend");
                System.out.println("Select one option from the above: ");

                int payOption = sc.nextInt();

                if(payOption == 1) {
                    System.out.println("1. Add in the existing group");
                    System.out.println("2. Create a new group");
                    System.out.println("Select one option from the above: ");

                    int groupOption = sc.nextInt();

                    if(groupOption == 1) {
                        System.out.println("List of available groups");
                        ArrayList<Group> groups = expenseRepository.getAllGroups();
                        for (int i = 0; i < groups.size(); i++) {
                            System.out.println((i+1)+". "+groups.get(i).getGroupName());
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

                        for (Group group: groups) {
                            if (group.getGroupName().equals(groupNameInput)) {
                                Expense newExpense = new Expense();
                                newExpense.setGroupId(group.getGroupId());
                                newExpense.setExpenseDesc(expenseDesc);
                                newExpense.setExpenseAmt(expenseAmt);
                                newExpense.setCurrencyType(currencyType);
                                boolean isTableExists = expenseRepository.tableExist("Expenses");
                                if (!isTableExists) {
                                    expenseRepository.createTable();
                                }
                                expenseRepository.save(newExpense);

                                System.out.println("1. Split equally");
                                System.out.println("2. Split unequally");
                                int divideType = sc.nextInt();

                                if (divideType == 1) {
                                    System.out.println("option 1 select");
                                } else if (divideType == 2) {
                                    System.out.println("option 2 select");
                                }
                                System.out.println("Succeed! expenses added");
                                return true;
                            }

                        }
                        System.out.println("Failed! Invalid entry! please enter valid group");
                        return false;
                    }
                }
            }
        }
    }
}
