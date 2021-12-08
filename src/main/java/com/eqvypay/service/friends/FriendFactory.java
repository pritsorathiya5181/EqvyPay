package com.eqvypay.service.friends;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class FriendFactory {

	private static FriendFactory friendFactory = null;
	private FriendRepository friendRepository;
	
	
	public FriendFactory() {
		
	}
	
	public static FriendFactory getInstance() {
		if(friendFactory == null) {
			friendFactory = new FriendFactory();
		}
		return friendFactory;
	}

	public FriendRepository getFriendRepository() {
		return friendRepository;
	}

	@Autowired
	public void setFriendRepository(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	
}
