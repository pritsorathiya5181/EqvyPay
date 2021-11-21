package com.eqvypay.Web;

import com.eqvypay.Service.ExpenseRepository;

import java.util.Scanner;

public class UserMenu {
    public void userOptions(ExpenseRepository expenseRepository) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("------------------------------");
            System.out.println("\tEqvypay Menu");
            System.out.println("------------------------------");
            System.out.println("1. Add a friend");
            System.out.println("2. Manage group");
            System.out.println("3. Manage expense");
            System.out.println("4. Activity");
            System.out.println("5. Profile details");
            System.out.println("6. Money manager");
            System.out.println("Select one option from the above");

            int option = sc.nextInt();
            switch (option) {
                case 3:
                    ManageExpenseOption manageExpenseOption = new ManageExpenseOption();
                    boolean status = manageExpenseOption.expenseOptions(expenseRepository);
                    System.out.println("expense status=="+status);
                    break;
                default:
                    System.out.println("Yet to implement");
            }
        }
    }
}
