package com.eqvypay.persistence;

import com.eqvypay.util.constants.enums.ExpenseType;

public class Expense implements IExpense {
	
	private String id;
	private String targetUserId;
	private String groupId;
	private ExpenseType expenseType;
    private float expenseAmount;
    private String expenseDesc;
    private String currencyType;
    private String sourceUserId;

    @Override
	public String getSourceUserId() {
		return sourceUserId;
	}

    @Override
	public void setSourceUserId(String sourceUserId) {
		this.sourceUserId = sourceUserId;
	}

    @Override
	public String getTargetUserId() {
		return targetUserId;
	}

    @Override
	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}

    @Override
	public String getGroupId() {
		return groupId;
	}

    @Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

    @Override
	public float getExpenseAmt() {
        return expenseAmount;
    }

    @Override
    public void setExpenseAmt(float expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    @Override
    public String getExpenseDesc() {
        return expenseDesc;
    }

    @Override
    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    @Override
    public String getCurrencyType() {
        return currencyType;
    }

    @Override
    public String getId() {
		return id;
	}

    @Override
	public void setId(String id) {
		this.id = id;
	}

    @Override
	public ExpenseType getExpenseType() {
		return expenseType;
	}
    
    @Override
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

    @Override
	public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
