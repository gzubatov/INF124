package com.uci.antsrusrestservice.db;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnector {

	private DatabaseConnector() {

	}

	public static Connection getConnection() {

		try {
			// Class.forName("org.mariadb.jdbc.Driver"); // Professor's DB, mine is MySQL
			String dbDriver = "com.mysql.cj.jdbc.Driver";
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// return DriverManager.getConnection("jdbc:mariadb://192.168.0.209/db4lecture",
			// "root", "mytest");
			Properties properties = new Properties();
			properties.setProperty("useSSL", "false");
			properties.setProperty("allowPublicKeyRetrieval", "true");
			properties.setProperty("serverTimezone", "UTC");
			properties.setProperty("user", "swantoma");
			properties.setProperty("password", "tmp123!");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/arts_and_crafts", properties);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

}
