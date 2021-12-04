package com.eqvypay.Web;

import java.util.Scanner;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.friends.FriendRepository;

public class RemoveFriendOption {
	public void friendOptions(User user,FriendRepository friendRepository) throws Exception {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("----------------------------");
            System.out.println("[1] Remove friend by email");
			System.out.println("[2] Remove friend by phone number");
			System.out.println("[3] Exit");
			
			int option = sc.nextInt();
			
			if(option == 3) {
				break;
			}
			
			switch(option) {
			case 1:
				System.out.println("Enter your friend's email id");
				sc.nextLine();
				String friendEmail = sc.nextLine();
				friendRepository.removeFriendByEmail(user, friendEmail);
				break;
			case 2: 
				System.out.println("Enter your friend's contact number");
				sc.nextLine();
				String friendContact = sc.nextLine();
				friendRepository.removeFriendByContact(user, friendContact);
			default:
				break;
			}
        }
	}
}
