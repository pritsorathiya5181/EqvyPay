package com.eqvypay.web;

import java.util.Scanner;

import com.eqvypay.persistence.User;
import com.eqvypay.service.authentication.AuthenticationService;
import com.eqvypay.service.friends.FriendRepository;
import com.eqvypay.util.validator.AuthenticationValidator;
import org.springframework.stereotype.Service;

@Service
public class AddFriendOption {
	public void friendOptions(User user,FriendRepository friendRepository) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("----------------------------");
            System.out.println("[1] Add friend by email");
			System.out.println("[2] Add friend by phone number");
			System.out.println("[3] Exit");
			System.out.print("Select an option: ");
			
			String option = sc.next();
			
			if(option.equals("3")) {
				break;
			}
			
			switch(option) {
			case "1":
				System.out.println("Enter your friend's email id");
				String friendEmail = AuthenticationValidator.getAndValidateEmail(sc);
				friendRepository.addFriendByEmail(user, friendEmail);
				break;
			case "2":
				System.out.print("Enter your friend's contact number: ");
				String friendContact = AuthenticationValidator.getAndValidateContact(sc);
				friendRepository.addFriendByContact(user, friendContact);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
        }
	}
}
