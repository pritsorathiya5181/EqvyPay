package com.eqvypay;

import com.eqvypay.service.user.UserRepository;
import com.eqvypay.service.authentication.AuthenticationService;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.service.expense.ExpenseRepository;
import com.eqvypay.service.moneymanager.MoneyManagerRepository;
import com.eqvypay.service.user.UserDataManipulation;
import com.eqvypay.util.DtoUtils;
import com.eqvypay.util.validator.AuthenticationValidator;
import com.eqvypay.web.UserMenu;

import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.eqvypay.persistence.User;

@SpringBootApplication(scanBasePackages = {"com.eqvypay.service", "com.eqvypay.web"})
public class EqvyPayApplication implements CommandLineRunner {
    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMenu userMenu;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MoneyManagerRepository moneyManagerRepository;

    @Autowired
    private DatabaseConnectionManagementService dcms;

    @Autowired
    private UserDataManipulation dataManipulation;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EqvyPayApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean test = Arrays.stream(env.getActiveProfiles()).anyMatch(profile -> profile.equals("test"));
        if (!test) {
            boolean loggedIn = false;
            while (true) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("----------------------");
                System.out.println("Welcome");
                System.out.println("----------------------");
                System.out.println("[1] Login");
                System.out.println("[2] Register");
                System.out.println("[3] Forgot Password");
                System.out.println("[0] Exit");
                System.out.println("Select an option: ");

                String option = scanner.next();
                User user = null;
                switch (option) {
                    case "0":
                        System.exit(0);
                    case "1":
                        String email = AuthenticationValidator.getAndValidateEmail(scanner);
                        String password = AuthenticationValidator.getAndValidatePassword(scanner);
                        if (DtoUtils.tableExist(dcms, "Users")) {
                            user = userRepository.getUserByEmailAndPassword(email, AuthenticationService.getHashedPassword(password));
                            if (!(user.getEmail() == null)) {
                                loggedIn = true;
                                System.out.println(user.getName() + " successfully logged in");
                            } else {
                                System.out.println("Invalid login credentials. Please try again.");
                            }
                        } else {
                            System.out.println("User not found.");
                        }
                        break;
                    case "2":
                        String name = AuthenticationValidator.getAndValidateName(scanner);
                        String registrationEmail = AuthenticationValidator.getAndValidateEmail(scanner);
                        String contact = AuthenticationValidator.getAndValidateContact(scanner);
                        String registrationPassword = AuthenticationValidator.getAndValidatePassword(scanner);
                        System.out.print("Confirm password: ");
                        String confirmPassword = AuthenticationValidator.getAndValidatePassword(scanner);
                        confirmPassword = AuthenticationValidator.getAndValidatePasswordAndConfirmPassword(scanner, registrationPassword, confirmPassword);
                        String securityAnswer = AuthenticationValidator.getAndValidateSecurityAnswer(scanner);
                        User newUser = new User();
                        newUser.setName(name);
                        newUser.setEmail(registrationEmail);
                        newUser.setContact(contact);
                        newUser.setPassword(AuthenticationService.getHashedPassword(registrationPassword));
                        newUser.setSecurityAnswer(securityAnswer);
                        dataManipulation.save(newUser);
                        break;
                    case 3:
                        String userEmail = AuthenticationValidator.getAndValidateEmail(scanner);
                        User oldUser = userRepository.getByEmail(userEmail);
                        System.out.println("What is your first school name?");
                        String providedSecurityAnswer = scanner.next();
                        if (providedSecurityAnswer.equals(oldUser.getSecurityAnswer())) {
                            String newPassword = AuthenticationValidator.getAndValidatePassword(scanner);
                            System.out.println("Confirm password");
                            String confirmNewPassword = AuthenticationValidator.getAndValidatePassword(scanner);
                            String finalConfirmNewPassword = AuthenticationValidator.getAndValidatePasswordAndConfirmPassword(scanner, newPassword, confirmNewPassword);
                            System.out.println("New: " + newPassword);
                            System.out.println("Confirmed: " + finalConfirmNewPassword);
                            if (newPassword.equals(finalConfirmNewPassword)) {
                                User updatedUser = oldUser;
                                updatedUser.setPassword(AuthenticationService.getHashedPassword(newPassword));
                                dataManipulation.delete(oldUser.getUuid());
                                dataManipulation.save(updatedUser);
                            }
                        } else {
                            System.out.println("Incorrect security answer, please try again");
                        }
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
                if (loggedIn) {
                    int ret = userMenu.userNewOptions(user);
                    if (ret == 8) {
                        loggedIn = false;
                    }
                }

            }

        }

    }
}
