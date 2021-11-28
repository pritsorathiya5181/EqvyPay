package com.eqvypay.Persistence;

public class PersonalExpense {
    private Float expenseAmt;
    private String expenseDesc;
    private String expenseCate;
    private String expenseDate;

    public Float getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(Float expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public String getExpenseDesc() {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc) {
        this.expenseDesc = expenseDesc;
    }

    public String getExpenseCate() {
        return expenseCate;
    }

    public void setExpenseCate(String expenseCate) {
        this.expenseCate = expenseCate;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
