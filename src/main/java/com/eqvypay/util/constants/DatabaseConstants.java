package com.eqvypay.util.constants;

public class DatabaseConstants {

	public static final String TEST_URL = "db.test_url";
	public static final String TEST_USERNAME = "db.test_username";
	public static final String TEST_PASSWORD = "db.test_password";

	//

	public static final String DEV_URL = "db.dev_url";
	public static final String DEV_USERNAME = "db.dev_username";
	public static final String DEV_PASSWORD = "db.dev_password";

	//

	public static final String PROD_URL = "db.prod_url";
	public static final String PROD_USERNAME = "db.prod_username";
	public static final String PROD_PASSWORD = "db.prod_password";

	// USER SQL STATEMENTS
	public static final String INSERT_USER = "INSERT INTO Users (uuid,name,email,contact,password,security_answer) VALUES (?,?,?,?,?,?)";

	// EXPENSE SQL STATEMENTS
	public static final String INSERT_EXPENSE = "INSERT INTO Expenses (groupId,expenseAmt,expenseDesc,currencyType) VALUES (?,?,?,?)";

	// GENERAL SQL
	public static final String SELECT = "SELECT * FROM %s ";



}
