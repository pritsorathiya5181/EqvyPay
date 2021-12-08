package com.eqvypay.persistence;

public class PersonalActivity implements IPersonalActivity {
    private String userId;
    private Float amount;
    private String description;
    private String expenseCategory;
    private String date;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public Float getAmount() {
        return amount;
    }

    @Override
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getExpenseCategory() {
        return expenseCategory;
    }

    @Override
    public void setExpenseCategory(String expenseCategory) { this.expenseCategory = expenseCategory; }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }
}
