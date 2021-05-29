package com.student.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionJDBC {
	private String url;
	private static ConnectionJDBC instance;

	private ConnectionJDBC() {
		String driver = null;
		try {
			//properties file
			ResourceBundle bundle = ResourceBundle.getBundle("connection");
			driver = bundle.getString("jdbc.driver");
			Class.forName(driver);
			url = bundle.getString("jdbc.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		if (instance == null) {
			instance = new ConnectionJDBC();
		}
		try {
			return DriverManager.getConnection(instance.url);
		} catch (SQLException e) {
			throw e;
		}
	}

	public static void close(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
