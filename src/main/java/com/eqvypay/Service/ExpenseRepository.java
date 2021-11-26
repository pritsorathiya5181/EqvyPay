package com.eqvypay.Service;

import java.util.ArrayList;
import java.util.List;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpenseRepository {
    public ArrayList<Group> getAllGroups() throws Exception;
    public List<Expense> getExpensesByUserId(String userId) throws Exception;
    public Expense save(Expense expense) throws Exception;
    public boolean saveAll(List<Expense> expenses) throws Exception;
    public void createTable() throws Exception;
    public boolean tableExist(String tableName) throws Exception;
    public boolean settleExpense(Expense expense) throws Exception;
}
