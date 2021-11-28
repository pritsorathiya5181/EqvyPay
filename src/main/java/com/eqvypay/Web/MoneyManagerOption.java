package com.eqvypay.Web;

import com.eqvypay.Persistence.PersonalActivity;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.MoneyManagerRepository;
import com.eqvypay.util.validator.DateValidator;
import com.eqvypay.util.validator.DateValidatorUsingDateFormat;

import java.util.ArrayList;
import java.util.Scanner;

public class MoneyManagerOption {

    public void handleOption(User user, MoneyManagerRepository moneyManagerRepository) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----------------------------");
            System.out.println("\tMoney Manager");
            System.out.println("----------------------------\n");
            System.out.println("1. Add income");
            System.out.println("2. Add personal expense");
            System.out.println("3. Show monthly statistics");
            System.out.println("Select one option from the above");

            int option = sc.nextInt();
            DateValidator validator = new DateValidatorUsingDateFormat("MM/dd/yyyy");
            PersonalActivity newActivity;

            switch (option) {
                case 1:
                    System.out.println("Enter your income amount");
                    float incomeValue = sc.nextFloat();
                    System.out.println("Enter description about your income");
                    sc.nextLine();
                    String incDesc = sc.nextLine();
                    System.out.println("Enter date of income (MM/DD/YYYY)");
                    String incomeDate = sc.next();
                    if (!validator.isDateValid(incomeDate)) {
                        System.out.println("Date format is not valid. Enter valid date");
                        break;
                    }

                    newActivity = new PersonalActivity();

                    newActivity.setUserId(user.getUuid().toString());
                    newActivity.setAmount(incomeValue);
                    newActivity.setDescription(incDesc);
                    newActivity.setExpenseCate("NA");
                    newActivity.setDate(incomeDate);

                    moneyManagerRepository.save(newActivity);
                    break;
                case 2:
                    System.out.println("Enter expense amount");
                    Float personalExpense = sc.nextFloat();
                    System.out.println("Enter expense description");
                    sc.nextLine();
                    String expenseDesc = sc.nextLine();
                    System.out.println("Enter expense category");
                    String expenseCat = sc.next();
                    System.out.println("Enter date of expenses (MM/DD/YYYY)");
                    String expenseDate = sc.next();
                    if (!validator.isDateValid(expenseDate)) {
                        System.out.println("Date format is not valid. Enter valid date");
                        break;
                    }

                    newActivity = new PersonalActivity();
                    String userId = user.getUuid().toString();

                    newActivity.setUserId(userId);
                    newActivity.setAmount(personalExpense);
                    newActivity.setDescription(expenseDesc);
                    newActivity.setExpenseCate(expenseCat);
                    newActivity.setDate(expenseDate);
                    moneyManagerRepository.save(newActivity);
                    break;
                case 3:
                    System.out.println("1. display by month");
                    System.out.println("2. display by category");
                    System.out.println("Select one option from the above");
                    int selectOption = sc.nextInt();
                    if (selectOption == 1) {
                        System.out.println("Enter a month");
                        sc.nextLine();
                        String month = sc.nextLine();

                        int monthNum = validator.getMonth(month);
                        ArrayList<PersonalActivity> activities = moneyManagerRepository.getActivities(user.getUuid().toString());
                        float totalIncome = 0;
                        float totalExpenditure = 0;
                        int minExpenditureIndex = 0;
                        int maxExpenditureIndex = 0;
                        boolean activityFound = false;
                        boolean hasMinExp = false;
                        boolean hasMaxExp = false;

                        for (int i = 0; i < activities.size(); i++) {
                            PersonalActivity activity = activities.get(i);
                            if (monthNum == Integer.parseInt(activity.getDate().split("/")[0])) {
                                if (!activityFound) {
                                    System.out.printf("%-15s%-15s%-15s%-15s%n", "sr.no.", "amount", "description", "category");
                                    activityFound = true;
                                }
                                Float amount = activity.getAmount();
                                if (activity.getExpenseCate().trim().equals("NA")) {
                                    totalIncome += amount;
                                } else {
                                    totalExpenditure += amount;
                                    if (amount > activities.get(maxExpenditureIndex).getAmount()) {
                                        hasMaxExp = true;
                                        maxExpenditureIndex = i;
                                    } else if (activities.get(minExpenditureIndex).getAmount() > amount) {
                                        hasMinExp = true;
                                        minExpenditureIndex = i;
                                    }
                                }
                            }
                            if (activityFound) {
                                System.out.printf("%-15s%-15s%-15s%-15s%n", i + 1, activity.getAmount(), activity.getDescription(), activity.getExpenseCate());
                            }
                        }
                        if (activityFound) {
                            System.out.println("\nTotal income: $" + totalIncome);
                            System.out.println("Total balance: " + (totalIncome > totalExpenditure ? ("$" + (totalIncome - totalExpenditure)) : "you have spent more than your earnings of this " + month));
                            if (hasMinExp) {
                                System.out.println("Minimum expenditure is $" + activities.get(minExpenditureIndex).getAmount() + " - on " + activities.get(minExpenditureIndex).getExpenseCate());
                            }
                            if (hasMaxExp) {
                                System.out.println("Minimum expenditure is $" + activities.get(maxExpenditureIndex).getAmount() + " - on " + activities.get(maxExpenditureIndex).getExpenseCate());
                            }
                        } else {
                            System.out.println("No activity for this " + month + " month");
                        }
                    } else if (selectOption == 2) {
                        System.out.println("Enter a category");
                        sc.nextLine();
                        String category = sc.nextLine();
                        ArrayList<PersonalActivity> activities = moneyManagerRepository.getActivities(user.getUuid().toString());

                        float totalIncome = 0;
                        float totalExpenditure = 0;
                        int minExpenditureIndex = 0;
                        int maxExpenditureIndex = 0;
                        boolean activityFound = false;
                        boolean hasMinExp = false;
                        boolean hasMaxExp = false;

                        for (int i = 0; i < activities.size(); i++) {
                            PersonalActivity activity = activities.get(i);
                            System.out.println("activity=="+activity.getExpenseCate());
                            if (category.equals(activity.getExpenseCate())) {
                                if (!activityFound) {
                                    System.out.printf("%-15s%-15s%-15s%-15s%n", "sr.no.", "amount", "description", "category");
                                    activityFound = true;
                                }
                                Float amount = activity.getAmount();
                                    if (activity.getExpenseCate().trim().equals("NA")) {
                                    totalIncome += amount;
                                } else {
                                    totalExpenditure += amount;
                                    if (amount > activities.get(maxExpenditureIndex).getAmount()) {
                                        hasMaxExp = true;
                                        maxExpenditureIndex = i;
                                    } else if (activities.get(minExpenditureIndex).getAmount() > amount) {
                                        hasMinExp = true;
                                        minExpenditureIndex = i;
                                    }
                                }
                            }
                            if (activityFound) {
                                System.out.printf("%-15s%-15s%-15s%-15s%n", i + 1, activity.getAmount(), activity.getDescription(), activity.getExpenseCate());
                            }
                        }

                        if (activityFound) {
                            System.out.println("\nTotal income: $" + totalIncome);
                            System.out.println("Total balance: " + (totalIncome > totalExpenditure ? ("$" + (totalIncome - totalExpenditure)) : "you have spent more than your earnings of this " + category));
                            if (hasMinExp) {
                                System.out.println("Minimum expenditure is $" + activities.get(minExpenditureIndex).getAmount() + " - on " + activities.get(minExpenditureIndex).getExpenseCate());
                            }
                            if (hasMaxExp) {
                                System.out.println("Minimum expenditure is $" + activities.get(maxExpenditureIndex).getAmount() + " - on " + activities.get(maxExpenditureIndex).getExpenseCate());
                            }
                        } else {
                            System.out.println("No activity for this " + category + " category");
                        }
                    }
            }
        }
    }
}
