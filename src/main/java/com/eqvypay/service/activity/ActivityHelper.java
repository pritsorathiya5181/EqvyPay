package com.eqvypay.service.activity;

import java.util.List;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Activity;

@Service
public class ActivityHelper {

	private static ActivityRepository activityRepository;
	
	@Autowired
	public void setActivityRepository(ActivityRepository activityRepository) {
		ActivityHelper.activityRepository = activityRepository;
	}
	
	public static void addActivity(String userId,String message) throws Exception {
		Activity activity = new Activity();
		activityRepository.addActivity(activity);
	}
	
	public static List<Activity> getActivity(String userId) throws Exception {
		return activityRepository.getUserActivity(userId);
	}
	
	public static void deleteActivity(String uuid) throws Exception {
		activityRepository.deleteActivity(uuid);
	}
	
}
