package com.eqvypay.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.eqvypay.Persistence.Expense;
import com.eqvypay.Persistence.Group;
import com.eqvypay.Persistence.User;
import com.eqvypay.util.constants.enums.ExpenseType;
import com.mysql.cj.protocol.ResultsetRow;

import ch.qos.logback.core.encoder.ByteArrayUtil;

public class DtoUtils {

	public static User getUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		while(resultSet.next()) {
			String id = resultSet.getString("uuid");
			String name = resultSet.getString("name");
			String email = resultSet.getString("email");
			String contact = resultSet.getString("contact");
			String securityAnswer = resultSet.getString("security_answer");
			user.setUuid(UUID.fromString(id));
			user.setName(name);
			user.setEmail(email);
			user.setContact(contact);
			user.setSecurityAnswer(securityAnswer);
		}
		return user;
	}
	public static List<Expense> getExpenseFromResultSet(ResultSet resultSet) throws Exception {
	
		List<Expense> expenses = new ArrayList<>();
		while(resultSet.next()) {
			String id = resultSet.getString("id");
			String targetUserId = resultSet.getString("targetUserId");
			String groupId=resultSet.getString("groupId");
			String expenseType = resultSet.getString("expenseType");
			float expenseAmt  = resultSet.getFloat("expenseAmt");
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
			//
		}
		return expenses;
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
	
	public static ArrayList<Group> getGroupsFromResultSet(ResultSet resultSet) throws SQLException {
		ArrayList<Group> groups = new ArrayList<Group>();
		Group group;
		while(resultSet.next()) {
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
	
	public static String getGroupIdByName(String groupName) throws SQLException {


		return "ds";

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
}
