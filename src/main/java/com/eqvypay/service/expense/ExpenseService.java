package com.eqvypay.service.expense;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.IExpense;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;

@Service
public class ExpenseService implements ExpenseRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public List<IExpense> getExpensesByUserId(String userId) throws Exception {
        if (dtoUtils.tableExist(dcms, "Expenses")) {
            Connection connection = dcms.getConnection(dcms.parseEnvironment());
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from Expenses where sourceUserId = '" + userId + "'"
                    + " OR targetUserId = '" + userId + "'");
            return dtoUtils.getExpenseFromResultSet(rs);
        } else {
            return null;
        }
    }

    @Override
    public boolean settleExpense(IExpense expense) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement statement = connection.createStatement();
        int count = statement.executeUpdate("DELETE from Expenses where id = '" + expense.getId() + "'");
        if (count > 0) {
            System.out.println("Success!");
            return true;
        }
        return false;
    }

}
