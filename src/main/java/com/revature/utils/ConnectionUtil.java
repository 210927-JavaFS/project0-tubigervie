package com.revature.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws SQLException
	{
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:postgresql://training-instance.capduaavzmdr.us-west-1.rds.amazonaws.com:5432/card_database";
		String username = "postgres"; //it is possible to use env variables to hide this information
		String password = "password"; //you would access them with System.getenv("varname");
		
		return DriverManager.getConnection(url, username, password);
	}

}
