package com.eqvypay.Service.expense;

import java.util.ArrayList;
import java.util.List;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository {
    ArrayList<Group> getAllJoinedGroups(User user) throws Exception;

    List<Expense> getExpensesByUserId(String userId) throws Exception;

//    Expense save(Expense expense) throws Exception;
//
//    boolean saveAll(List<Expense> expenses) throws Exception;
//
//    void createTable() throws Exception;
//
//    boolean tableExist(String tableName) throws Exception;

    boolean settleExpense(Expense expense) throws Exception;

    List<User> findAllFriends(String userId) throws Exception;
}
