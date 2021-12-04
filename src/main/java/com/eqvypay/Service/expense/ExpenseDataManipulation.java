package com.eqvypay.Service.expense;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseDataManipulation implements IExpenseDataManipulation {

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Override
    public void createTable() throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        Statement s = connection.createStatement();
        s.executeUpdate("CREATE TABLE Expenses"
                + " ( id varchar(255)"
                + ",sourceUserId varchar(255)"
                + ",targetUserId varchar(266)"
                + ",groupId varchar(255)"
                + " ,expenseType varchar(255)"
                + " ,expenseAmt float"
                + " ,expenseDesc varchar(255)"
                + " ,currencyType varchar(255) );"
        );
    }

    @Override
    public Expense save(Expense expense) throws Exception {
        System.out.println("trying to save expense");
        expense.setId(UUID.randomUUID().toString());
        return expense;
    }

    @Override
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

    @Override
    public boolean saveAll(List<Expense> expenses) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        for (Expense expense : expenses) {
            PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_EXPENSE);
            preparedStatement.setString(1, expense.getId());
            preparedStatement.setString(2, expense.getSourceUserId());
            preparedStatement.setString(3, expense.getTargetUserId());
            preparedStatement.setString(4, expense.getGroupId());
            preparedStatement.setString(5, expense.getExpenseType().getType());
            preparedStatement.setFloat(6, expense.getExpenseAmt());
            preparedStatement.setString(7, expense.getExpenseDesc());
            preparedStatement.setString(8, expense.getCurrencyType());
            try {
                int count = preparedStatement.executeUpdate();
                if (count > 0) {
                    System.out.println("Expense Added into Db");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
