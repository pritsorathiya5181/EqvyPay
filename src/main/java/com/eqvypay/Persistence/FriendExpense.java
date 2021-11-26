package com.eqvypay.Persistence;

public class FriendExpense {

	private String uuid;
	private String expenseId;
	private String sourceUserId;
	private String targetUserId;
	
	//
	
	public String getExpenseId() {
		return expenseId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}
	public String getSourceUserId() {
		return sourceUserId;
	}
	public void setSourceUserId(String sourceUserId) {
		this.sourceUserId = sourceUserId;
	}
	public String getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

	
	
}
