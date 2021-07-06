package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	public static Connection DBConnection() {
		Connection conn;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:hms.db");
			return conn;
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return null;
		}
	}
}
