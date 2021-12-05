package com.eqvypay.service.profile;

import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.repository.UserRepository;
import com.eqvypay.util.constants.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

public class ProfileDataManipulation implements IProfileDataManipulation{

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserDataManipulation dataManipulation;

    @Override
    public void getProfile(User user) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        user = dataManipulation.getByEmail(user.getEmail());

        System.out.println("\nProfile Details for " + user.getName());
        System.out.println("Username: " + user.getName());
        System.out.println("Email id: " + user.getEmail());
        System.out.println("Contact number: " + user.getContact());

    }
}
