package com.eqvypay.persistence;

import java.util.UUID;

public class Income {
    private UUID incomeId;
    private String userId;
    private Float incomeValue;
    private String incomeDesc;

    public Income() {
        this.incomeId = UUID.randomUUID();
    }

    public String getIncomeId() {
        return incomeId.toString();
    }

    public void setIncomeId(UUID incomeId) {
        this.incomeId = incomeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Float getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(Float incomeValue) {
        this.incomeValue = incomeValue;
    }

    public String getIncomeDesc() {
        return incomeDesc;
    }

    public void setIncomeDesc(String incomeDesc) {
        this.incomeDesc = incomeDesc;
    }
}
