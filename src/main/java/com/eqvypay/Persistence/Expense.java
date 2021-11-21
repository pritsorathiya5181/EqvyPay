package com.eqvypay.Persistence;

public class Expense {
    private String groupId;
    private float expenseAmt;
    private String expenseDesc;
    private String currencyType;

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

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
