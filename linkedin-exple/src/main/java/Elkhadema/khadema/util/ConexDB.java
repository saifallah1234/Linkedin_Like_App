package Elkhadema.khadema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexDB {
	private static Connection connexion;
	private final String DB_URL = "jdbc:mysql://localhost:3306/khademadb";
	private final String USER = "root";
	private final String PASS = "";

	private ConexDB() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("17 " + e);
		}
		connexion = DriverManager.getConnection(DB_URL, USER, PASS);
	}

	public static Connection getInstance() {
		if (connexion == null) {
			try {
				new ConexDB();
			} catch (SQLException e) {
				System.out.println(e);

			}
		}
		return connexion;

	}
}
