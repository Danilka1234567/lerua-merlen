package infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static Connection currentConn;

    static{

        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't find postgre sql driver!", e);
        }


        Dotenv dotenv = Dotenv.load();
        URL = "jdbc:postgresql://" + dotenv.get("DB_HOST") + ":" + dotenv.get("DB_PORT") + "/" + dotenv.get("DB_NAME");
        USERNAME = dotenv.get("DB_USER");
        PASSWORD = dotenv.get("DB_PASSWORD");


        establishConnection();
    }

    private static void establishConnection(){

        try{
            currentConn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection with DB", e);
        }

    }


    public static Connection getConnectionSingletone(){
        try{

            if(currentConn.isClosed())
                establishConnection();

            currentConn.setAutoCommit(true);
            return currentConn;
        } catch (SQLException e) {
            throw new RuntimeException("Major conn exception", e);
        }
    }
}
