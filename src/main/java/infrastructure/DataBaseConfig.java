package infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class DataBaseConfig {

    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:5432/test-db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "postgres";

    static{

        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL driver not found", e);
        }

    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
    }

}
