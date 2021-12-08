package com.eqvypay.service.activity;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eqvypay.persistence.Activity;

@Repository
public interface ActivityRepository {

	void addActivity(Activity activity) throws Exception;
	List<Activity> getUserActivity(String userId) throws Exception;
	void deleteActivity(String uuid) throws Exception;
	void createTable() throws Exception;
}
