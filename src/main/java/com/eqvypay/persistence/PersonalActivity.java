package com.eqvypay.persistence;

public class PersonalActivity implements IPersonalActivity{
    private String userId;
    private Float amount;
    private String description;
    private String expenseCategory;
    private String date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) { this.expenseCategory = expenseCategory; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
