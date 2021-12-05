package com.eqvypay.service.repository;


import java.util.List;

import com.eqvypay.persistence.Expense;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends IRepositories {
    List<Expense> getExpensesByUserId(String userId) throws Exception;

    boolean settleExpense(Expense expense) throws Exception;

}
