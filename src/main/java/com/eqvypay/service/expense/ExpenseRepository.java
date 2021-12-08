package com.eqvypay.service.expense;


import java.util.List;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.IExpense;

import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository{
    List<IExpense> getExpensesByUserId(String userId) throws Exception;

    boolean settleExpense(IExpense expense) throws Exception;

}
