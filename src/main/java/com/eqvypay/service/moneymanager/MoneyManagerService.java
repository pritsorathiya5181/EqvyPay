package com.eqvypay.service.moneymanager;

import com.eqvypay.persistence.*;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.DatabaseConstants;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Service
public class MoneyManagerService implements MoneyManagerRepository {

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public void addIncomeExpense(PersonalActivity activity) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        PreparedStatement preparedStatement = connection.prepareStatement(DatabaseConstants.INSERT_PERSONAL_ACTIVITY);

        preparedStatement.setString(1, activity.getUserId());
        preparedStatement.setString(2, activity.getAmount().toString());
        preparedStatement.setString(3, activity.getDescription());
        preparedStatement.setString(4, activity.getExpenseCategory());
        preparedStatement.setString(5, activity.getDate());
        int count = preparedStatement.executeUpdate();
        if (count > 0) {
            System.out.println("Activity added successfully");
        }
    }

    @Override
    public ArrayList<PersonalActivity> getActivities(String userId) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * from PersonalActivities WHERE userId ='" + userId + "'");
        return dtoUtils.getAllActivities(resultSet);
    }

}
