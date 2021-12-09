package com.eqvypay.service.expense;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.IExpense;

import java.util.List;

public interface IExpenseDataManipulation {
    public void createTable() throws Exception;

    public IExpense save(IExpense expense) throws Exception;

    public void saveAll(List<IExpense> expenses) throws Exception;
}
