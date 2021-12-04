package com.eqvypay.Web;

import com.eqvypay.Persistence.PersonalActivity;
import com.eqvypay.Persistence.User;
import com.eqvypay.Service.moneymanager.MoneyManagerDataManipulation;
import com.eqvypay.Service.moneymanager.MoneyManagerRepository;
import com.eqvypay.util.formatter.NumberFormatUsingFormatter;
import com.eqvypay.util.formatter.NumberFormatter;
import com.eqvypay.util.validator.DateValidator;
import com.eqvypay.util.validator.DateValidatorUsingDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class MoneyManagerOption {

    @Autowired
    MoneyManagerDataManipulation dataManipulation;

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

            try {
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
                        newActivity.setExpenseCategory("NA");
                        newActivity.setDate(incomeDate);

                        dataManipulation.createTable();
                        moneyManagerRepository.addIncomeExpense(newActivity);
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
                        newActivity.setExpenseCategory(expenseCat);
                        newActivity.setDate(expenseDate);

                        dataManipulation.createTable();
                        moneyManagerRepository.addIncomeExpense(newActivity);
                        break;
                    case 3:
                        NumberFormatter numberFormatter = new NumberFormatUsingFormatter();

                        System.out.println("1. display by month");
                        System.out.println("2. display by category");
                        System.out.println("Select one option from the above");

                        int selectOption = 0;
                        selectOption = sc.nextInt();

                        if (selectOption == 1) {
                            System.out.println("Enter a month");
                            sc.nextLine();
                            String month = sc.nextLine();

                            int monthNum = validator.getMonth(month);
                            ArrayList<PersonalActivity> activities = moneyManagerRepository.getActivities(user.getUuid().toString());
                            float totalIncome = 0;
                            float totalExpenditure = 0;
                            int minExpenditureIndex = -1;
                            int maxExpenditureIndex = -1;
                            boolean activityFound = false;
                            boolean hasMaxExp = false;

                            if (monthNum > 0) {
                                for (int i = 0; i < activities.size(); i++) {
                                    PersonalActivity activity = activities.get(i);

                                    if (monthNum == Integer.parseInt(activity.getDate().split("/")[0])) {
                                        if (!activityFound) {
                                            System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "sr.no.", "amount", "description", "category", "date");
                                            activityFound = true;
                                        }
                                        Float amount = activity.getAmount();

                                        if (activity.getExpenseCategory().trim().equals("NA")) {
                                            totalIncome += amount;
                                        } else {
                                            float maxValue = maxExpenditureIndex > -1 ? activities.get(maxExpenditureIndex).getAmount() : 0;
                                            float minValue;

                                            if (minExpenditureIndex > -1) {
                                                minValue = activities.get(minExpenditureIndex).getAmount();
                                            } else {
                                                minValue = activities.get(i).getAmount();
                                                minExpenditureIndex = i;
                                            }
                                            totalExpenditure += amount;
                                            if (amount > maxValue) {
                                                hasMaxExp = true;
                                                maxExpenditureIndex = i;
                                            } else if (minValue > amount) {
                                                minExpenditureIndex = i;
                                            }
                                        }
                                    }
                                    if (activityFound) {
                                        System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", i + 1, activity.getAmount(), activity.getDescription(), activity.getExpenseCategory(), activity.getDate());
                                    }
                                }
                            }
                            if (activityFound) {
                                System.out.println("\nTotal income: $" + numberFormatter.formatNumber(totalIncome));
                                System.out.println("Total balance: " + (totalIncome > totalExpenditure ? ("$" + numberFormatter.formatNumber(totalIncome - totalExpenditure)) : "you have spent more than your earnings of this " + month));
                                System.out.println("Minimum expenditure is $" + activities.get(minExpenditureIndex).getAmount() + " - on " + activities.get(minExpenditureIndex).getExpenseCategory());

                                if (hasMaxExp) {
                                    System.out.println("Maximum expenditure is $" + activities.get(maxExpenditureIndex).getAmount() + " - on " + activities.get(maxExpenditureIndex).getExpenseCategory());
                                }
                            } else {
                                System.out.println("No activity for this " + month + " month");
                            }
                        } else if (selectOption == 2) {
                            System.out.println("Enter a category");
                            sc.nextLine();
                            String category = sc.nextLine();
                            ArrayList<PersonalActivity> activities = moneyManagerRepository.getActivities(user.getUuid().toString());

                            float totalExpenditure = 0;
                            int minExpenditureIndex = 0;
                            int maxExpenditureIndex = -1;
                            boolean activityFound = false;
                            boolean hasMinExp = false;
                            boolean hasMaxExp = false;

                            for (int i = 0; i < activities.size(); i++) {
                                PersonalActivity activity = activities.get(i);

                                if (category.equals(activity.getExpenseCategory())) {
                                    if (!activityFound) {
                                        System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "sr.no.", "amount", "description", "category", "date");
                                        activityFound = true;
                                    }

                                    Float amount = activity.getAmount();

                                    totalExpenditure += amount;
                                    float maxValue = maxExpenditureIndex > -1 ? activities.get(maxExpenditureIndex).getAmount() : 0;
                                    float minValue = activities.get(minExpenditureIndex).getAmount();
                                    if (amount > maxValue) {
                                        hasMaxExp = true;
                                        maxExpenditureIndex = i;
                                    }
                                    if (minValue > amount) {
                                        hasMinExp = true;
                                        minExpenditureIndex = i;
                                    }
                                    System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", i + 1, activity.getAmount(), activity.getDescription(), activity.getExpenseCategory(), activity.getDate());
                                }
                            }

                            if (activityFound) {
                                System.out.println("\nTotal Expenditure: $" + numberFormatter.formatNumber(totalExpenditure));
                                if (hasMinExp) {
                                    System.out.println("Minimum expenditure is $" + activities.get(minExpenditureIndex).getAmount() + " - on " + activities.get(minExpenditureIndex).getExpenseCategory());
                                }
                                if (hasMaxExp && minExpenditureIndex != maxExpenditureIndex) {
                                    System.out.println("Maximum expenditure is $" + activities.get(maxExpenditureIndex).getAmount() + " - on " + activities.get(maxExpenditureIndex).getExpenseCategory());
                                }
                            } else {
                                System.out.println("No activity for this " + category + " category");
                            }
                        }
                        break;
                    default:
                        System.out.println("Please select a valid option");
                }

            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Input Mismatch! please add valid input");
            }

        }
    }
}
