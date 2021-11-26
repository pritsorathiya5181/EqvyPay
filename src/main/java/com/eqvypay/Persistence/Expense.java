package com.eqvypay.Persistence;

import com.eqvypay.util.constants.enums.ExpenseType;

public class Expense {
	
	private String id;
	private String targetUserId;
	private String groupId;
	private ExpenseType expenseType;
    private float expenseAmt;
    private String expenseDesc;
    private String currencyType;
    private String sourceUserId;

    //
    
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public float getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(float expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
