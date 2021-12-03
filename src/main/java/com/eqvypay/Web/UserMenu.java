package com.eqvypay.Web;

import com.eqvypay.Persistence.User;
import com.eqvypay.Service.ExpenseRepository;
import com.eqvypay.Service.FriendRepository;
import com.eqvypay.Service.FriendService;
import com.eqvypay.Service.GroupRepository;
import com.eqvypay.Service.ProfileRepository;

import java.util.Scanner;

import com.eqvypay.Service.MoneyManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMenu {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private MoneyManagerRepository moneyManagerRepository;
	
	@Autowired
	private FriendRepository friendRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	public int userNewOptions(User user) throws Exception {
		  Scanner sc = new Scanner(System.in);
	        while (true) {
				System.out.println("------------------------------");
				System.out.println("\tEqvypay Menu");
				System.out.println("------------------------------");
				System.out.println("[1] Add a friend");
				System.out.println("[2] Remove a friend");
				System.out.println("[3] Manage group");
				System.out.println("[4] Manage expense");
				System.out.println("[5] Activity");
				System.out.println("[6] Profile details");
				System.out.println("[7] Money manager");
				System.out.println("[8] Logout");
				System.out.println("Select an option: ");

				int option = sc.nextInt();
				if (option == 8) {
					return 8;
				} else {


					switch (option) {
						case 1:
							System.out.println("Add friend option selected");
							AddFriendOption addFriendOption = new AddFriendOption();
							addFriendOption.friendOptions(user, friendRepository);
							break;
						case 2:
							System.out.println("Remove friend option selected");
							RemoveFriendOption removeFriendOption = new RemoveFriendOption();
							removeFriendOption.friendOptions(user, friendRepository);
							break;
						case 3:
							System.out.println("Manage Groups");
							ManageGroupOption manageGroupOption = new ManageGroupOption();
							manageGroupOption.groupOptions(user, groupRepository);
							break;
						case 4:
							System.out.println("Manage option selected");
							ManageExpenseOption manageExpenseOption = new ManageExpenseOption();
							boolean status = manageExpenseOption.expenseOptions(user, expenseRepository);
							System.out.println("expense status==" + status);
							break;
						case 5:
							break;
						case 6:
							UpdateProfileOption profileOptions = new UpdateProfileOption();
							profileOptions.updateProfileOption(user, profileRepository);
							break;
						default:
							System.out.println("Yet to implement");
					}
				}
				return 0;
			}
	}
	
    public void userOptions(User user,ExpenseRepository expenseRepository) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("------------------------------");
            System.out.println("\tEqvypay Menu");
            System.out.println("------------------------------");
            System.out.println("[1] Add a friend");
            System.out.println("[2] Remove a friend");
            System.out.println("[3] Manage group");
            System.out.println("[4] Manage expense");
            System.out.println("[5] Activity");
            System.out.println("[6] Profile details");
            System.out.println("[7] Money manager");
            System.out.println("[8] Exit");

            int option = sc.nextInt();
            
            if(option == 8) {
				break;
			}
            
            switch (option) {
	            case 1:
	        		AddFriendOption addFriendOption = new AddFriendOption();
	        		addFriendOption.friendOptions(user, friendRepository);
	        		break;
	        	case 2:
	        		RemoveFriendOption removeFriendOption = new RemoveFriendOption();
	        		removeFriendOption.friendOptions(user, friendRepository);
	        		break;
	        	case 3:
	        		break;
	            case 4:
                    ManageExpenseOption manageExpenseOption = new ManageExpenseOption();
                    boolean status = manageExpenseOption.expenseOptions(user,expenseRepository);
                    System.out.println("expense status=="+status);
                    break;
	            case 5:
	            	break;
	            case 6:
	            	UpdateProfileOption profileOptions = new UpdateProfileOption();
	            	profileOptions.updateProfileOption(user, profileRepository);
	            	break;
                default:
                    System.out.println("Yet to implement");
            }
        }
    }
}
