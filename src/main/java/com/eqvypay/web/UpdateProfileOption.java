package com.eqvypay.web;

import java.util.Scanner;

import com.eqvypay.persistence.User;
import com.eqvypay.service.profile.ProfileRepository;
import org.springframework.stereotype.Service;
import com.eqvypay.service.authentication.AuthenticationService;
import com.eqvypay.util.validator.AuthenticationValidator;

@Service
public class UpdateProfileOption {


    public void updateProfileOption(User user, ProfileRepository profileRepo) throws Exception {

        String input = "";
        String menuInput = "";
        do {
            showUserInfo(user);
            Scanner scanner = new Scanner(System.in);


            System.out.println("Would you like to update any information? [Y/N]");
            input = scanner.next();
            if (input.equalsIgnoreCase("y")) {
                while (true) {
                    System.out.println("------------------------------------------");
                    System.out.println("Which information would you like to update?");
                    System.out.println("[1] Update username");
                    System.out.println("[2] Update contact number");
                    System.out.println("[3] Update password");
                    System.out.println("[4] Exit");
                    menuInput = scanner.next();
                    Integer option = 0;
                    option = Integer.valueOf(menuInput);

                    switch (option) {
                        case 1:
                            System.out.println("Enter the new username");
                            String username = scanner.next();
                            profileRepo.updateUsername(user, username);
                            System.out.println("Username updated successfully.");
                            break;
                        case 2:
                            System.out.println("Enter the new contact number");
                            String contact = scanner.next();
                            profileRepo.updateContact(user, contact);
                            System.out.println("Username updated successfully.");
                            break;
                        case 3:
                            String currentPassword = user.getPassword();
                            System.out.print("Current password: ");
                            String providedCurrentPassword = AuthenticationService.getHashedPassword(AuthenticationValidator.getAndValidatePassword(scanner));
                            String providedNewPassword = "";
                            if (currentPassword.equals(providedCurrentPassword)) {
                                System.out.print("New password: ");
                                providedNewPassword = AuthenticationValidator.getAndValidatePassword(scanner);
                                profileRepo.updatePassword(user, providedNewPassword);
                            } else {
                                System.out.println("Incorrect current password.");
                            }
                            break;
                        case 4:
                            System.out.println("");
                        default:
                            break;
                    }
                }

            }else{
                break;
            }
        }
        while (input.equalsIgnoreCase("y"));


    }

    private void showUserInfo(User user) {
        System.out.println("------------------------------");
        System.out.println("\tMy Account");
        System.out.println("------------------------------");
        System.out.println("User name:\t" + user.getName());
        System.out.println("User email:\t" + user.getEmail());
        System.out.println("Contact:\t" + user.getContact());
        System.out.println("------------------------------");

    }
}
