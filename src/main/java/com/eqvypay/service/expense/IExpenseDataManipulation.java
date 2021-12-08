package com.eqvypay.service.expense;

import com.eqvypay.persistence.IExpense;

import java.util.List;

public interface IExpenseDataManipulation {
    public void createTable() throws Exception;

    public boolean tableExist(String tableName) throws Exception;

    public boolean saveAll(List<IExpense> expenses) throws Exception;

	IExpense save(IExpense expense) throws Exception;
}
