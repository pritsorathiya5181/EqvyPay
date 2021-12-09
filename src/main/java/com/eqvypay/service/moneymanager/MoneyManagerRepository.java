package com.eqvypay.service.moneymanager;

import com.eqvypay.persistence.IPersonalActivity;
import com.eqvypay.persistence.PersonalActivity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoneyManagerRepository {
    void addIncomeExpense(IPersonalActivity activity) throws Exception;

    ArrayList<IPersonalActivity> getActivities(String userId) throws Exception;

}
