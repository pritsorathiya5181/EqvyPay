package com.eqvypay.persistence;

public class PersonalExpense {
    private Float expenseAmount;
    private String expenseDesc;
    private String expenseCategory;
    private String expenseDate;

    public Float getExpenseAmt() {
        return expenseAmount;
    }

    public void setExpenseAmt(Float expenseAmt) {
        this.expenseAmount = expenseAmt;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    public String getExpenseCate() {
        return expenseCategory;
    }

    public void setExpenseCate(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
