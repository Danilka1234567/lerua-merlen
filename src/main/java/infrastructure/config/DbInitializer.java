package infrastructure.config;

import infrastructure.utils.ResourceReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbInitializer {

    public static void init(){

        Connection conn = ConnectionManager.getConnectionSingletone();


        try(Statement statement = conn.createStatement();){

            String directory = "sql/ddl/";
            statement.execute(ResourceReader.read(directory + "create_manufacturer.sql"));
            statement.execute(ResourceReader.read(directory + "create_warehouse.sql"));
            statement.execute(ResourceReader.read(directory + "create_user.sql"));
            statement.execute(ResourceReader.read(directory + "create_session.sql"));
            statement.execute(ResourceReader.read(directory + "create_product.sql"));
            statement.execute(ResourceReader.read(directory + "create_order.sql"));

        }catch (SQLException e){
            throw new RuntimeException("Failed to init DB!", e);
        }
    }

}
