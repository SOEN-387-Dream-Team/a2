package soen387;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnConstants {

    private DatabaseConnConstants() {
    }

    public static final String DB_USER = "admin";
    public static final String DB_PASSWORD = "admin";

    public static final Connection CONNECTION;

    static {
        try {
            CONNECTION = DriverManager.getConnection("jdbc:mysql://localhost:3306/soen387_school", DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
