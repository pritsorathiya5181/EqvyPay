package com.eqvypay.service.expense;

import com.eqvypay.persistence.Expense;

import java.util.List;

public interface IExpenseDataManipulation {
    public void createTable() throws Exception;

    public Expense save(Expense expense) throws Exception;

    public void saveAll(List<Expense> expenses) throws Exception;
}
