package com.eqvypay.service.expense;


import java.util.List;

import com.eqvypay.persistence.Expense;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository{
    List<Expense> getExpensesByUserId(String userId) throws Exception;

    boolean settleExpense(Expense expense) throws Exception;

}
