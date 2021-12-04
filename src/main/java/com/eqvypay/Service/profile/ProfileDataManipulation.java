package com.eqvypay.Service.profile;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.database.DatabaseConnectionManagementService;
import com.eqvypay.Service.user.UserDataManipulation;
import com.eqvypay.Service.user.UserRepository;
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
