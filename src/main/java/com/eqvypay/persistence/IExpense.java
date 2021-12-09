package com.eqvypay.persistence;

import com.eqvypay.util.constants.enums.ExpenseType;

public interface IExpense {
	public String getSourceUserId();
	public void setSourceUserId(String sourceUserId);
	public String getTargetUserId();
	public void setTargetUserId(String targetUserId);
	public String getGroupId();
	public void setGroupId(String groupId);
	public float getExpenseAmt();
    public void setExpenseAmt(float expenseAmount);
    public String getExpenseDesc();
    public void setExpenseDesc(String expenseDesc);
    public String getCurrencyType();
    public String getId();
	public void setId(String id);
	public ExpenseType getExpenseType();
	public void setExpenseType(ExpenseType expenseType);
	public void setCurrencyType(String currencyType);
}
