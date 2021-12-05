package com.eqvypay.Service.expense;


import java.util.List;

import com.eqvypay.Persistence.Expense;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository {
    List<Expense> getExpensesByUserId(String userId) throws Exception;

    boolean settleExpense(Expense expense) throws Exception;

}
