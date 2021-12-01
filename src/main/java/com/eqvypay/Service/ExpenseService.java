package com.eqvypay.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.ExpenseDto;
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
    public Expense save(Expense expense) throws Exception {
    	System.out.println("trying to save expense");
        expense.setId(UUID.randomUUID().toString());
        return expense;
    }
    

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
                + " ,currencyType varchar(255)"
                + " ,timestamp varchar(255) );"
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

	@Override
	public boolean saveAll(List<Expense> expenses) throws Exception {
			Connection connection = dcms.getConnection(Environment.DEV);
	        for(Expense expense:expenses) {
			PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_EXPENSE);
			preparedStatement.setString(1,expense.getId());
	        preparedStatement.setString(2,expense.getSourceUserId());
	        preparedStatement.setString(3,expense.getTargetUserId());
	        preparedStatement.setString(4,expense.getGroupId());
	        preparedStatement.setString(5,expense.getExpenseType().getType());
	        preparedStatement.setFloat(6, expense.getExpenseAmt());
	        preparedStatement.setString(7, expense.getExpenseDesc());
	        preparedStatement.setString(8, expense.getCurrencyType());
	        preparedStatement.setString(9, LocalDateTime.now().toString());
	        try {
	            int count = preparedStatement.executeUpdate();
	            if(count>0) {
	                System.out.println("Expense Added into Db");
	            }
	        		
			} catch (Exception e) {
				e.printStackTrace();
			}
	        }
		return true;
	}

	@Override
	public List<Expense> getExpensesByUserId(String userId) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * from Expenses where sourceUserId = '"+userId+"'"
				+ " OR targetUserId = '"+userId+"'");
		return DtoUtils.getExpenseFromResultSet(rs);
	}

	@Override
	public boolean settleExpense(Expense expense) throws Exception {
		Connection connection = dcms.getConnection(Environment.DEV);
		Statement statement = connection.createStatement();
		int count = statement.executeUpdate("DELETE from Expenses where id = '"+expense.getId()+"'");
		if(count>0) {
			System.out.println("Success!");
			return true;
		}
		return false;
	}
}
