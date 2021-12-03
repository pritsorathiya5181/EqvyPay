package com.eqvypay;

import com.eqvypay.Service.*;
import com.eqvypay.Web.ManageExpenseOption;
import com.eqvypay.Service.ExpenseRepository;
import com.eqvypay.Web.UserMenu;

import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.eqvypay.Persistence.User;

@SpringBootApplication(scanBasePackages = {"com.eqvypay.Service", "com.eqvypay.Web"})
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

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EqvyPayApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Welcome, Login and Registration

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
                System.out.println("[0] for exit");
                System.out.println("Select an option: ");

                Integer option = scanner.nextInt();
                User user = null;
                switch (option) {
                    case 0:
                        System.exit(0);
                    case 1:
                        System.out.println("Enter email");
                        String email = scanner.next();
                        System.out.println("Enter password");
                        String password = scanner.next();
//			System.out.println("Hashed password is "+AuthenticationService.getHashedPassword(password));
                        user = userRepository.getUserByEmailAndPassword(email, AuthenticationService.getHashedPassword(password));
                        if (!(user.getEmail() == null)) {
                            loggedIn = true;
                            System.out.println(user.getName() + " successfully logged in");
                        } else {
                            System.out.println("Invalid login credentials. Please try again.");
//							main(args);
                        }
                        break;
                    case 2:
                        System.out.println("Enter your name");
                        String name = scanner.next();
                        System.out.println("Enter your email");
                        String registrationEmail = scanner.next();
                        System.out.println("Enter your phone number");
                        String contact = scanner.next();
                        System.out.println("Enter your password");
                        String registrationPassword = scanner.next();
                        System.out.println("Enter confirm password");
                        String confirmPassword = scanner.next();
                        System.out.println("What is your first school name");
                        String securityAnswer = scanner.next();
                        User newUser = new User();
                        newUser.setName(name);
                        newUser.setEmail(registrationEmail);
                        newUser.setContact(contact);
                        newUser.setPassword(AuthenticationService.getHashedPassword(registrationPassword));
                        newUser.setSecurityAnswer(securityAnswer);
                        userRepository.save(newUser);
                        main(args);
                        break;
                    case 3:
                        System.out.println("Enter your email");
                        String userEmail = scanner.next();
                        User oldUser = userRepository.getByEmail(userEmail);
                        System.out.println("What is your first school name");
                        String providedSecurityAnswer = scanner.next();
                        if (providedSecurityAnswer.equals(oldUser.getSecurityAnswer())) {
                            System.out.println("Enter passowrd");
                            String newPassword = scanner.next();
                            System.out.println("Enter confirm password");
                            String confirmNewPassword = scanner.next();
                            oldUser.setPassword(newPassword);
                            userRepository.save(oldUser);
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
                    // just testing manage expense
                    //	ManageExpenseOption manageExpenseOption = new ManageExpenseOption();
                    //System.out.println("Managing options for user "+user.getEmail());
                    //boolean done = manageExpenseOption.expenseOptions(user,expenseRepository);

                }
                //                System.out.println("Started Application in Test Mode");

            }

        }
    }
}
