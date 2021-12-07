package com.eqvypay.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Activity;
import com.eqvypay.persistence.User;
import com.eqvypay.service.activity.ActivityRepository;

@Service
public class ActivityOption {

	@Autowired
	private ActivityRepository activityRepository;
	
	public void getActivity(User user) throws Exception {
		List<Activity> activities = activityRepository.getUserActivity(user.getUuid().toString());
		Integer count = 1;
		for(Activity activity:activities) {
			System.out.format("%-10s%-30s\n", count.toString().concat("."),activity.getMessage());
			count++;
		}
	}
	
}
