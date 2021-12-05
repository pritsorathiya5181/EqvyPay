package com.eqvypay.util.validator;

import java.util.Scanner;

public class RegistrationValidator {

	public static String getAndValidateName(Scanner scanner) {
		String name = "";
		while (true) {
			System.out.println("Enter name");
			name = scanner.next();
			if (name == null) {
				System.out.println("Name cannot be null");
				continue;
			} else if (name.length() < 3) {
				System.out.println("Name should atleast contain three characters");
				continue;
			}
			break;
		}
		return name;
	}


	public static String getAndValidateSecurityAnswer(Scanner scanner) {
		String answer = "";
		while (true) {
			System.out.println("What is your first school name?");
			answer = scanner.next();
			if (answer == null) {
				System.out.println("Security answer must not be null");
				continue;
			} else {
				break;
			}
		}
		return answer;
	}

	public static String getAndValidatePasswordAndConfirmPassword(Scanner scanner, String password, String confirmPassword) {
		String newPassword = confirmPassword;
		while (true) {
			if (!(password.equals(newPassword))) {
				System.out.println("Password and Confirm password must be same");
				System.out.print("Confirm password:");
				newPassword = AuthenticationValidator.getAndValidatePassword(scanner);
			} else {
				return newPassword;
			}
		}

	}

	public static String getAndValidateContact(Scanner scanner) {
		String contact = "";
		while (true) {
			System.out.println("Enter contact number");
			contact = scanner.next();
			if (contact == null) {
				System.out.println("Contact cannot be null");
				continue;
			} else if (contact.length() != 10 || !contact.matches("^[0-9]*$")) {
				System.out.println("Contact must contain digits and must be atleast 10 digits long");
				continue;
			} else {
				break;
			}
		}
		return contact;

	}
}
