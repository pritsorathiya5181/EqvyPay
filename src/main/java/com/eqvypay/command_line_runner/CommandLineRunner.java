package com.eqvypay.command_line_runner;

import java.util.Scanner;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.FriendService;

public class CommandLineRunner {

	public void commandLineRunner() throws Exception {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		while (true) {
			System.out.println("*************** Welcome to Eqvypay ***************");
			System.out.println("Please select an option from the below menu ");
			System.out.println("[1] Login/Sign up");
			System.out.println("[2] Add Friend");
			System.out.println("[3] Manage Group");
			System.out.println("[4] Manage Expense");
			System.out.println("[5] Activity Logs");
			System.out.println("[6] Profile Details");
			System.out.println("[7] Money Manager");
			System.out.println("[8] Quit");
			try {
				input = scanner.nextLine();
			} catch (Exception e1) {
			}

			Integer option = 0;
			try {
				option = Integer.valueOf(input);
			} catch (NumberFormatException e) {
				System.out.println("Unknown Input. Please try again!");
				continue;
			}
			if (option == 8) {
				break;
			}

			switch (option) {
			case 1:
				break;
			case 2:
				User user = new User();
				user.setContact("7806404405");
				user.setEmail("prit");
				user.setName("prit");
				user.setPassword("asd");
				FriendService friendService = new FriendService();
				friendService.addFriend(user);
				
			case 8:
				break;
			default:
				System.out.println("\nUnknown Input. Please try again!\n");
				break;
			}

		}

		scanner.close();
	}
}
