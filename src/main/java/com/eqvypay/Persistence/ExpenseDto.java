package com.eqvypay.Persistence;

import java.util.List;

public class ExpenseDto {

    private String id;
    private float expenseAmount;
    private String expenseDesc;
    private String currencyType;
    private String groupId;
    private List<String> userId;

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
    }

    public float getExpenseAmt() {
        return expenseAmount;
    }

    public void setExpenseAmt(float expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        this.userId = userId;
    }


}
