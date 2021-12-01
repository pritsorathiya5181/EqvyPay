package com.eqvypay.Web;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ExpenseRepository;
import com.eqvypay.util.constants.Constants;

public class ActivityMenu {

	public boolean activityOptions(User user,ExpenseRepository expenseRepository) throws Exception {
    	Scanner sc = new Scanner(System.in);
		System.out.println("Your outstandings are");
    	List<Expense> expenses = expenseRepository.getExpensesByUserId(user.getUuid().toString());
    	for(Expense expense : expenses) {
			Currency currency = Currency.getInstance(expense.getCurrencyType());
    		if(expense.getSourceUserId().equals(user.getUuid().toString()) ) {
    			System.out.format(Constants.settleSource, expense.getTargetUserId(), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()) );
    			System.out.println();
    		}else {
    			System.out.format(Constants.settleTarget, expense.getSourceUserId(), String.valueOf(expense.getExpenseAmt()).concat(currency.getSymbol()) );
    			System.out.println();
    		}
    	}
    	return true;
    }

}
