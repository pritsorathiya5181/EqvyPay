package com.eqvypay.Service;

import java.util.ArrayList;
import java.util.UUID;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import org.springframework.stereotype.Repository;

import com.eqvypay.Persistence.User;

@Repository
public interface ExpenseRepository {
    public ArrayList<Group> getAllGroups() throws Exception;
    public void save(Expense expense) throws Exception;
    public void createTable() throws Exception;
    public boolean tableExist(String tableName) throws Exception;
}
