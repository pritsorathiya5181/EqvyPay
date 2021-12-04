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

    boolean settleExpense(Expense expense) throws Exception;

    List<User> findAllFriends(String userId) throws Exception;
}
