package com.eqvypay.Service;

import com.eqvypay.Persistence.PersonalActivity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface MoneyManagerRepository {
    void createTable() throws Exception;

    void save(PersonalActivity activity) throws Exception;

    ArrayList<PersonalActivity> getActivities(String userId) throws Exception;

}
