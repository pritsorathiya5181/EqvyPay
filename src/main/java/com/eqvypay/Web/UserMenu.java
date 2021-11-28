package com.eqvypay.Web;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ExpenseRepository;

import java.util.Scanner;

import com.eqvypay.Service.MoneyManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMenu {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private MoneyManagerRepository moneyManagerRepository;
	
	public void userNewOptions(User user) throws Exception {
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
	                	System.out.println("Manage option selected");
	                    ManageExpenseOption manageExpenseOption = new ManageExpenseOption();
	                    boolean status = manageExpenseOption.expenseOptions(user,expenseRepository);
	                    System.out.println("expense status=="+status);
	                    break;
					case 6:
						System.out.println("Money manager option selected");
						MoneyManagerOption moneyManagerOption = new MoneyManagerOption();
						moneyManagerOption.handleOption(user, moneyManagerRepository);
	                default:
	                    System.out.println("Yet to implement");
	            }
	        }
	}
	
    public void userOptions(User user,ExpenseRepository expenseRepository) throws Exception {
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
                    boolean status = manageExpenseOption.expenseOptions(user,expenseRepository);
                    System.out.println("expense status=="+status);
                    break;
                default:
                    System.out.println("Yet to implement");
            }
        }
    }
}
