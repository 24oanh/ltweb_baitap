package week2.Collection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBConnect {
	private final String serverName = "localhost";
	private final String dbName = "QLBH";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "sa";
	private final String password = "1";

	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + "\\" + instance + ":" + portNumber + ";databaseName=" + dbName
				+ ";encrypt=false;trustServerCertificate=true;";
		if (instance == null || instance.trim().isEmpty())
			url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName
					+ ";encrypt=false;trustServerCertificate=true;";
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}

	public static void main(String[] args) {
		String sqlInsert = "INSERT INTO users VALUES(?, ?, ?)";
		String selectAll = "SELECT * FROM users";
		try {
			// connect to database
			Connection conn = new DBConnect().getConnection();
			// crate statement to insert GiaoVien
			PreparedStatement stmt = conn.prepareStatement(sqlInsert);
			stmt.setInt(1, 1);
			stmt.setString(2, "Trung");
			stmt.setString(3, "HCM");
			stmt.execute();
			// select all GiaoVien
			stmt = conn.prepareStatement(selectAll);
			// get data from table GiaoVien
			ResultSet rs = stmt.executeQuery();
			// show data
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
