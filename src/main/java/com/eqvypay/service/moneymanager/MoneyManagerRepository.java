package com.eqvypay.service.moneymanager;

import com.eqvypay.persistence.PersonalActivity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoneyManagerRepository {
    void addIncomeExpense(PersonalActivity activity) throws Exception;

    ArrayList<PersonalActivity> getActivities(String userId) throws Exception;

}
