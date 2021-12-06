package com.eqvypay.service.moneymanager;

import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Statement;

@Service
public class MoneyManagerDataManipulation implements IMoneyManagerDataManipulation {

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Autowired
    DtoUtils dtoUtils;

    @Override
    public void createTable() throws Exception {
        String query = "CREATE TABLE PersonalActivities"
                + " ( userId varchar(255)"
                + ",amount float"
                + ",description varchar(255)"
                + ",expenseCate varchar(255)"
                + " ,date varchar(255) );";

        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        Statement s = connection.createStatement();
        String tableName = "PersonalActivities";

        if (!dtoUtils.tableExist(dcms, tableName)) {
            s.executeUpdate(query);
        }
    }
}
