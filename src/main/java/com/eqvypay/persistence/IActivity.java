package com.eqvypay.persistence;

public interface IActivity {
	String getUuid();
	void setUuid(String uuid);
	String getUserId();
	void setUserId(String userId);
	String getMessage();
	void setMessage(String message);
}
