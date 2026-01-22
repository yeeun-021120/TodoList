package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "jsl26",
                "1234"
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
