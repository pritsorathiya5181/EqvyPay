package com.eqvypay.util.validator;

import java.util.Scanner;

public class AuthenticationValidator {
    public static String getAndValidateEmail(Scanner scanner) {
        String email = "";
        while (true) {
            System.out.println("Enter email");
            email = scanner.next();
            if (!email.matches("^[\\S]+\\@[\\w]+\\.[a-zA-Z]{2,4}$")) {
                System.out.println("Email must be of proper format ( example@example.com )");
            } else {
                break;
            }
        }
        return email;
    }

    public static String getAndValidatePassword(Scanner scanner) {
        String password = "";
        while (true) {
            System.out.println("Enter your password");
            password = scanner.next();
            if (password == null) {
                System.out.println("Password must not be empty");
                continue;
            }
            if (password.length() < 8) {
                System.out.println("Password must contain atleast 8 characters");
                continue;
            }
            if (password.replaceAll("[^\\w]", "").length() == password.length()) {
                System.out.println("Password must contain atleast one special character and one capital letter");
                continue;
            } else {
                break;
            }
        }
        return password;
    }


}
