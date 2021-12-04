package com.eqvypay.Service.moneymanager;

import com.eqvypay.Service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.Statement;

public class MoneyManagerDataManipulation implements IMoneyManagerDataManipulation {

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Override
    public void createTable() throws Exception {
        String query = "CREATE TABLE PersonalActivities"
                + " ( userId varchar(255)"
                + ",amount float"
                + ",description varchar(255)"
                + ",expenseCate varchar(255)"
                + " ,date varchar(255) );";

        Connection connection = dcms.getConnection(Environment.DEV);
        Statement s = connection.createStatement();
        String tableName = "PersonalActivities";

        if (!DtoUtils.tableExist(dcms, tableName)) {
            s.executeUpdate(query);
        }
    }
}
