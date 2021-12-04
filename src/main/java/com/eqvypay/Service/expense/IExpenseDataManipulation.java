package com.eqvypay.Service.expense;

import com.eqvypay.Persistence.Expense;

import java.util.List;

public interface IExpenseDataManipulation {
    public void createTable() throws Exception;

    public Expense save(Expense expense) throws Exception;

    public boolean tableExist(String tableName) throws Exception;

    public boolean saveAll(List<Expense> expenses) throws Exception;
}
