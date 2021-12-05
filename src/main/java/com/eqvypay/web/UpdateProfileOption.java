package com.eqvypay.web;

import java.util.Scanner;

import com.eqvypay.persistence.User;
import com.eqvypay.service.profile.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProfileOption {
	
	public void updateProfileOption(User user, ProfileRepository profileRepo) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		while(true) {
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
			
			if(option == 4) {
				break;
			}

			switch (option) {
				case 1:
					System.out.println("Enter the new username");
					String username = scanner.next();
					profileRepo.updateUsername(user, username);
					break;
				case 2:
					System.out.println("Enter the new contact number");
					String contact = scanner.next();
					profileRepo.updateContact(user, contact);
					break;
				case 3: 
					System.out.println("Enter the new password");
					String password = scanner.next();
					profileRepo.updatePassword(user, password);
					break;
				default:
					break;
			}
		}
	}
}
