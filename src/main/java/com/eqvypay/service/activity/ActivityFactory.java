package com.eqvypay.service.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eqvypay.persistence.Activity;
import com.eqvypay.persistence.IActivity;

@Service
public class ActivityFactory {

    private static ActivityFactory activityFactory = null;

    @Autowired
    private ActivityRepository activityRepository;

    private IActivity activity;

    public static ActivityFactory getInstance() {
        if (activityFactory == null) {
            activityFactory = new ActivityFactory();
        }
        return activityFactory;
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public IActivity getActivity() {
        activity = new Activity();
        return activity;
    }


}
