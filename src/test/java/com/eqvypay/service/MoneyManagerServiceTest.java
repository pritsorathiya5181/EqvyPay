package com.eqvypay.service;

import com.eqvypay.persistence.PersonalActivity;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.moneymanager.MoneyManagerDataManipulation;
import com.eqvypay.service.moneymanager.MoneyManagerRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import com.eqvypay.util.constants.Environment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@SpringBootTest
public class MoneyManagerServiceTest {

    @Autowired
    MoneyManagerDataManipulation moneyManagerDataManipulation;

    @Autowired
    MoneyManagerRepository moneyManagerRepository;

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    UserDataManipulation userDataManipulation;

    @Autowired
    UserRepository userRepository;

    private static Connection connection;

    @Test
    @Order(1)
    public void testAddIncomeExpense() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        moneyManagerDataManipulation.createTable();
        User user = userRepository.getByEmail("hirva@gmail.com");

        PersonalActivity personalActivity = new PersonalActivity();
        personalActivity.setAmount(50F);
        personalActivity.setDate("12/05/2021");
        personalActivity.setExpenseCategory("food");
        personalActivity.setDescription("grocery");
        personalActivity.setUserId(user.getUuid().toString());

        moneyManagerRepository.addIncomeExpense(personalActivity);
        PreparedStatement selectQuery = connection.prepareStatement("select * from PersonalActivities where userId = ?");
        selectQuery.setString(1, user.getUuid().toString());
        ResultSet result = selectQuery.executeQuery();
        while(result.next()){
            Assertions.assertEquals(result.getFloat("amount"), 50);
            Assertions.assertEquals(result.getString("date"), "12/05/2021");
            Assertions.assertEquals(result.getString("expenseCate"), "food");
            Assertions.assertEquals(result.getString("description"), "grocery");
        }
    }

    @Test
    @Order(2)
    public void testGetActivities() throws Exception {
        connection = dcms.getConnection(Environment.TEST);

        User user = userRepository.getByEmail("hirva@gmail.com");
        ArrayList<PersonalActivity> activities = moneyManagerRepository.getActivities(user.getUuid().toString());
        for(PersonalActivity activity : activities){
            Assertions.assertEquals(activity.getAmount(), 50);
            Assertions.assertEquals(activity.getDate(), "12/05/2021");
            Assertions.assertEquals(activity.getExpenseCategory(), "food");
            Assertions.assertEquals(activity.getDescription(), "grocery");
        }
    }

}
