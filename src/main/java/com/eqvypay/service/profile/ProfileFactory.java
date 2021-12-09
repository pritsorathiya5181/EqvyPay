package com.eqvypay.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfileFactory {

	private static ProfileFactory profileFactory=null;
	
	@Autowired
	private IProfileDataManipulation profileDataManipulation;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public static ProfileFactory getInstance() {
		if(profileFactory == null) {
			profileFactory = new ProfileFactory();
		}
		return profileFactory;
	}

	public IProfileDataManipulation getProfileDataManipulation() {
		return profileDataManipulation;
	}

	public ProfileRepository getProfileRepository() {
		return profileRepository;
	}
	
}
