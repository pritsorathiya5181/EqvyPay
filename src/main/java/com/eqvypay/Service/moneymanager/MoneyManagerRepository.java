package com.eqvypay.Service.moneymanager;

import com.eqvypay.Persistence.PersonalActivity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoneyManagerRepository {
    void addIncomeExpense(PersonalActivity activity) throws Exception;

    ArrayList<PersonalActivity> getActivities(String userId) throws Exception;

}
