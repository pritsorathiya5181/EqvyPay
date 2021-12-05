package com.eqvypay.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.eqvypay.persistence.Expense;
import com.eqvypay.persistence.Group;
import com.eqvypay.persistence.PersonalActivity;
import com.eqvypay.persistence.User;
import com.eqvypay.service.database.DatabaseConnectionManagementService;
import com.eqvypay.util.constants.Environment;
import com.eqvypay.util.constants.enums.ExpenseType;

public class DtoUtils {

    public static boolean tableExist(DatabaseConnectionManagementService dcms, String tableName) throws Exception {
        Connection connection = dcms.getConnection(Environment.DEV);
        boolean tableExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tableExists = true;
                    break;
                }
            }
        }
        return tableExists;
    }

	public static int getCountOfRecords(ResultSet rs){
		int count = 0;
//		System.out.println(rs);
		try {
			while (rs.next())
				count++;
		}catch (Exception e){
			System.out.println(e.toString());
		}
		return count;
	}
	

	public static List<String> getIdFromResultSet(ResultSet resultSet) {
		List<String> ids = new ArrayList<>();
		try {
			while (resultSet.next()){
				ids.add(resultSet.getString("group_id"));
			}
		}catch (Exception e){
			System.out.println(e.toString());
		}
		return ids;

	}
    public static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        while (resultSet.next()) {
            String id = resultSet.getString("uuid");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String contact = resultSet.getString("contact");
            String securityAnswer = resultSet.getString("security_answer");
            String password = resultSet.getString("password");
            user.setUuid(UUID.fromString(id));
            user.setName(name);
            user.setEmail(email);
            user.setContact(contact);
            user.setSecurityAnswer(securityAnswer);
            user.setPassword(password);
        }
        return user;
    }

    public static List<Expense> getExpenseFromResultSet(ResultSet resultSet) throws Exception {

        List<Expense> expenses = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String targetUserId = resultSet.getString("targetUserId");
            String groupId = resultSet.getString("groupId");
            String expenseType = resultSet.getString("expenseType");
            float expenseAmt = resultSet.getFloat("expenseAmt");
            String expenseDesc = resultSet.getString("expenseDesc");
            String currencyType = resultSet.getString("currencyType");
            String sourceUserId = resultSet.getString("sourceUserId");

            Expense expense = new Expense();
            expense.setId(id);
            expense.setCurrencyType(currencyType);
            expense.setExpenseAmt(expenseAmt);
            expense.setExpenseType(ExpenseType.valueOf(expenseType));
            expense.setGroupId(groupId);
            expense.setSourceUserId(sourceUserId);
            expense.setTargetUserId(targetUserId);
            expense.setExpenseDesc(expenseDesc);

            expenses.add(expense);
        }
        return expenses;
    }

    public static ArrayList<Group> getGroupsFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Group> groups = new ArrayList<Group>();
        Group group;
        while (resultSet.next()) {
            group = new Group();
            String groupId = resultSet.getString("group_id");
            String groupName = resultSet.getString("group_name");
            String groupDesc = resultSet.getString("group_desc");
            group.setGroupId(groupId);
            group.setGroupName(groupName);
            group.setGroupDesc(groupDesc);
            groups.add(group);
        }
        return groups;
    }

    public static ArrayList<PersonalActivity> getAllActivities(ResultSet resultSet) throws SQLException {
        ArrayList<PersonalActivity> activities = new ArrayList<PersonalActivity>();
        PersonalActivity activity;
        while (resultSet.next()) {
            activity = new PersonalActivity();
            String userId = resultSet.getString("userId");
            String amount = resultSet.getString("amount");
            String description = resultSet.getString("description");
            String expenseCategory = resultSet.getString("expenseCate");
            String date = resultSet.getString("date");

            activity.setUserId(userId);
            activity.setAmount(Float.parseFloat(amount));
            activity.setDescription(description);
            activity.setExpenseCategory(expenseCategory);
            activity.setDate(date);
            activities.add(activity);
        }
        return activities;
    }

    public static ArrayList<User> getAllFriendsFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<User> friends = new ArrayList<User>();
        User friend;
        while (resultSet.next()) {
            friend = new User();
            String friendId = resultSet.getString("friend_id");
            String friendName = resultSet.getString("name");
            String friendEmail = resultSet.getString("email");

            friend.setUuid(UUID.fromString(friendId));
            friend.setName(friendName);
            friend.setEmail(friendEmail);

            friends.add(friend);
        }
        return friends;
    }
}
