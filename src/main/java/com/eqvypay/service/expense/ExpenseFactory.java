package com.eqvypay.service.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.IExpense;

@Service
public class ExpenseFactory {

	private static ExpenseFactory expenseFactory = null;
	
	@Autowired
	IExpenseDataManipulation expenseDataManipulation;
	
	@Autowired
	ExpenseRepository expenseRepository;
	
	IExpense expense;
	
	public ExpenseFactory() {
//		expenseDataManipulation = new ExpenseDataManipulation();
	//	expenseRepository = new ExpenseService();
		expense = new Expense();
	}
	
	public static ExpenseFactory getInstance() {
		if(expenseFactory==null) {
			expenseFactory = new ExpenseFactory();
		}
		return expenseFactory;
	}
	
	public IExpenseDataManipulation getExpenseDataManipulation() {
		return expenseDataManipulation;
	}

	public ExpenseRepository getExpenseRepository() {
		return expenseRepository;
	}

	public IExpense getExpense() {
		expense = new Expense();
		return expense;
	}
	
	
}
