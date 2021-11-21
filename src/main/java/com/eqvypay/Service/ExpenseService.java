package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
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
    public ArrayList<Group> getAllGroups() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from test_group");
        return DtoUtils.getGroupsFromResultSet(resultSet);
    }

    @Override
    public void save(Expense expense) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_EXPENSE);
        preparedStatement.setString(1, expense.getGroupId());
        preparedStatement.setFloat(2, expense.getExpenseAmt());
        preparedStatement.setString(3, expense.getExpenseDesc());
        preparedStatement.setString(4, expense.getCurrencyType());
        int count = preparedStatement.executeUpdate();
        if(count>0) {
            System.out.println("Expense Added into Db");
        }
    }

    @Override
    public void createTable() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement s = connection.createStatement();
        s.executeUpdate("CREATE TABLE Expenses"
                + " ( groupId varchar(255)"
                + " ,expenseId varchar(255)"
                + " ,expenseAmt float"
                + " ,expenseDesc varchar(255)"
                + " ,currencyType varchar(255) );"
        );
    }

    public boolean tableExist(String tableName) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        boolean tableExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tableExists = true;
                    break;
                }
            }
        }
        return tableExists;
    }
}
