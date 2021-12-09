package com.eqvypay.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IUser;
import com.eqvypay.persistence.User;

@Service
public class UserFactory {
	private static UserFactory userFactory = null;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private IUserDataManipulation userDataManipuation;
	
	private IUser user;
	
	public static UserFactory getInstance() {
		if(userFactory == null) {
			userFactory = new UserFactory();
		}
		return userFactory;
	}

	public IUserDataManipulation getUserDataManipuation() {
		return userDataManipuation;
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}

	public IUser getUser() {
		user = new User();
		return user;
	}

	
}
