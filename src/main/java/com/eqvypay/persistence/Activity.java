package com.eqvypay.persistence;


public class Activity implements IActivity {
	
	private String uuid;
	private String userId;
	private String message;

	@Override
	public String getUuid() {
		return uuid;
	}
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String getUserId() {
		return userId;
	}
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public String getMessage() {
		return message;
	}
	@Override
	public void setMessage(String message) {
		this.message = message;
	}
		
	
}
