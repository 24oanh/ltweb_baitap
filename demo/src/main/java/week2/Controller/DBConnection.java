package week2.Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String SERVER_NAME = "localhost";
    private static final String DB_NAME = "QLDN";
    private static final String PORT_NUMBER = "1433";
    private static final String INSTANCE = ""; // nếu có instance thì điền, nếu không thì để rỗng
    private static final String USER_ID = "sa";
    private static final String PASSWORD = "1";

    public static Connection getConnection() throws Exception {
        String url;
        if (INSTANCE == null || INSTANCE.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + SERVER_NAME + ":" + PORT_NUMBER
                    + ";databaseName=" + DB_NAME
                    + ";encrypt=false;trustServerCertificate=true;";
        } else {
            url = "jdbc:sqlserver://" + SERVER_NAME + "\\" + INSTANCE + ":" + PORT_NUMBER
                    + ";databaseName=" + DB_NAME
                    + ";encrypt=false;trustServerCertificate=true;";
        }

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, USER_ID, PASSWORD);
    }

    // test kết nối
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Kết nối SQL Server thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
