package com.eqvypay.util.validator;

import java.util.Scanner;

public class RegistrationValidator {

	public static String getAndValidateName(Scanner scanner) {
		String name = "";
		while(true) {
			System.out.println("Enter name");
			name = scanner.next();
			if(name == null) {
				System.out.println("Name cannot be null");
				continue;
			}
			else if(name.length()<3) {
				System.out.println("Name should atleast contain three characters");
				continue;
			}
			break;
		}
		return name;
	}
	
	public static String getAndValidateEmail(Scanner scanner) {
		String email = "";
		while(true) {
			System.out.println("Enter email");
			email = scanner.next();
			if(!email.matches("^[\\S]+\\@[\\w]+\\.[a-zA-Z]{2,4}$")) {
			System.out.println("Email must be of proper format ( example@example.com )");
			continue;
			}else {
				break;
			}
		}
		return email;
	}
	public static String getAndValidatePassword(Scanner scanner) {
		String password = "";
		while(true) {
		System.out.println("Enter your password");
		password = scanner.next();
		if(password==null) {
			System.out.println("Password must not be empty");
			continue;
		}
		else if(password.length()<8) {
			System.out.println("Password must contain atleast 8 characters");
			continue;
		}
		else if(password.replaceAll("[^\\w]","").length()==password.length()) {
			System.out.println("Password must contain atleast one special character and one capital letter");
			continue;
		}
		else {
			break;
		}
		}
		return password;
	}
	public static String getAndValidateSecurityAnswer(Scanner scanner) {
		String answer = "";
		while(true) {
			System.out.println("What is your first school name");	
				answer = scanner.next(); 
			if(answer == null) {
				System.out.println("Security answer must not be null");
				continue;
			}else {
				break;
			}
		}
		return answer;
	}
	public static String getAndValidatePasswordAndConfirmPassword(Scanner scanner,String password,String confirmPassword) {
		while(true) {
			if(!(password.equals(confirmPassword))) {
			System.out.println("Password and Confirm password must be same");
			getAndValidatePassword(scanner);
			}else {
				break;
			}
		}
		return confirmPassword;
	}

	public static String getAndValidateContact(Scanner scanner) {
		String contact = "";
		while(true) {
			System.out.println("Enter contact number");
			contact = scanner.next();
			if(contact==null) {
				System.out.println("Contact cannot be null");
				continue;
			}
			else if(contact.length()<8 || contact.matches("(A-Z)+")) {
				System.out.println(contact.length());
				System.out.println(contact.matches("(A-Z)+"));
				System.out.println("Contact must contain digits and must me atleast 8 digits long");
				continue;
			}
			else {
				break;
			}
		}
		return contact;
			
	}
}
