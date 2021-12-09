package com.eqvypay.service.profile;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.service.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class ProfileDataManipulation implements IProfileDataManipulation{

    @Autowired
    DatabaseConnectionManagementService dcms;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserDataManipulation dataManipulation;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getProfile(IUser user) throws Exception {
        Connection connection = dcms.getConnection(dcms.parseEnvironment());
        user = userRepository.getByEmail(user.getEmail());

        System.out.println("\nProfile Details for " + user.getName());
        System.out.println("Username: " + user.getName());
        System.out.println("Email id: " + user.getEmail());
        System.out.println("Contact number: " + user.getContact());

    }
}
