package com.eqvypay.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.eqvypay.Persistence.User;

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
}
