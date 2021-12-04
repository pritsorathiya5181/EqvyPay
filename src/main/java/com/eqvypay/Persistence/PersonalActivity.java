package com.eqvypay.Persistence;

public class PersonalActivity {
    private String userId;
    private Float amount;
    private String description;
    private String expenseCate;
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

    public String getExpenseCate() {
        return expenseCate;
    }

    public void setExpenseCate(String expenseCate) {
        this.expenseCate = expenseCate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
