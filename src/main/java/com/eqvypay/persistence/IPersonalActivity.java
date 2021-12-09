package com.eqvypay.persistence;

public interface IPersonalActivity {
	  public String getUserId();
	  public void setUserId(String userId);
	  public Float getAmount();
	  public void setAmount(Float amount);
	  public String getDescription();
	  public void setDescription(String description);
	  public String getExpenseCategory();
	  public void setExpenseCategory(String expenseCategory);
	  public String getDate();
	  public void setDate(String date);
}
