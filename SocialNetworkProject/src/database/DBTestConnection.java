package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTestConnection {
	private static DBTestConnection instance;

	private Connection connection;

	private static final String SSL_PARAMETER = "useSSL=false";
	private static final String DB_SCHEMA = "mydb";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "localhost";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "003131";

	private DBTestConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA + "?" + SSL_PARAMETER,
				DB_USERNAME, DB_PASSWORD);
	}

	public static DBTestConnection getInstance() {
		if (instance == null) {
			try {
				instance = new DBTestConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_SCHEMA,
					DB_USERNAME, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
