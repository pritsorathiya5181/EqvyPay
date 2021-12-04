package com.eqvypay.Service.expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import com.eqvypay.Service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.constants.DatabaseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.Persistence.User;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;

@Service
public class ExpenseService implements ExpenseRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Override
    public ArrayList<Group> getAllJoinedGroups(User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM Groups INNER JOIN GroupMembers on Groups.group_id = GroupMembers.group_id where uuid = '" + user.getUuid().toString() + "'";
        ResultSet resultSet = statement.executeQuery(query);
        return DtoUtils.getGroupsFromResultSet(resultSet);
    }

    @Override
    public List<Expense> getExpensesByUserId(String userId) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * from Expenses where sourceUserId = '" + userId + "'"
                + " OR targetUserId = '" + userId + "'");
        return DtoUtils.getExpenseFromResultSet(rs);
    }

    @Override
    public boolean settleExpense(Expense expense) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        int count = statement.executeUpdate("DELETE from Expenses where id = '" + expense.getId() + "'");
        if (count > 0) {
            System.out.println("Success!");
            return true;
        }
        return false;
    }

    @Override
    public List<User> findAllFriends(String userId) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from Friend inner join Users on Friend.friend_id = Users.uuid where Friend.user_id ='" + userId + "'");
        return DtoUtils.getAllFriendsFromResultSet(rs);
    }
}
