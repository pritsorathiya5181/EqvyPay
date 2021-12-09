package com.eqvypay.service.activity;

import java.util.List;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.IActivity;

/**
 * @param userId userUUID of the user.
 * @return List<IActivity> Object of the user.
 * @throws Exception if any error occurs while performing
 *                   operation of fetching activity information
 *                   from the Activity table.
 */

@Service
public class ActivityHelper {

	private static ActivityRepository activityRepository;
	private static IActivityDataManipulation activityDataManipulation;
	
	@Autowired
	public static void setActivityDataManipulation(IActivityDataManipulation activityDataManipulation) {
		ActivityHelper.activityDataManipulation = activityDataManipulation;
	}

	@Autowired
	public void setActivityRepository(ActivityRepository activityRepository) {
		ActivityHelper.activityRepository = activityRepository;
	}
	

	public static void addActivity(String userId,String message) throws Exception {
		IActivity activity = ActivityFactory.getInstance().getActivity();
		activity.setUuid(UUID.randomUUID().toString());
		activity.setMessage(message);
		activity.setUserId(userId);
		activityDataManipulation.addActivity(activity);
	}
	
	public static List<IActivity> getActivity(String userId) throws Exception {
		return activityRepository.getUserActivity(userId);
	}
	
	public static void deleteActivity(String uuid) throws Exception {
		activityDataManipulation.deleteActivity(uuid);
	}
	
	
	
}
