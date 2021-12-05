package com.eqvypay.Web;

import java.util.Scanner;

import com.eqvypay.Service.AuthenticationService;
import com.eqvypay.Service.UserRepository;
import com.eqvypay.util.validator.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ProfileRepository;

public class UpdateProfileOption {

    public void updateProfileOption(User user, ProfileRepository profileRepo) throws Exception {

        showUserInfo(user);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Would you like to update any information? [Y/N]");
        String input = scanner.next();

        if (input.equalsIgnoreCase("y")) {
            while (true) {
                System.out.println("------------------------------------------");
                System.out.println("Which information would you like to update?");
                System.out.println("[1] Update username");
                System.out.println("[2] Update contact number");
                System.out.println("[3] Update password");
                System.out.println("[4] Exit");
                try {
                    input = scanner.next();
                } catch (Exception e1) {
                }

                Integer option = 0;
                try {
                    option = Integer.valueOf(input);
                } catch (NumberFormatException e) {
                    System.out.println("\nUnknown Input. Please try again!\n");
                    continue;
                }

                if (option == 4) {
                    break;
                }

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
                    default:
                        break;
                }
            }


        }


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
