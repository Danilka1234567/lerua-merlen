package infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void initialize(){

        try(Connection conn = getConnection()){

            Statement creationStatement = conn.createStatement();

            String userSql  = """
                    CREATE TABLE IF NOT EXISTS users (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(64) NOT NULL,
                    surname VARCHAR(64) NOT NULL,
                    phone_number VARCHAR(12) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE
                    );""";

            String warehouseSql = """
                    CREATE TABLE IF NOT EXISTS warehouses (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(64) NOT NULL,
                    address VARCHAR(255) NOT NULL UNIQUE,
                    phone_number VARCHAR(12) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    capacity INT NOT NULL CHECK (capacity > 0)
                    );""";

            String manufacturerSql = """
                    CREATE TABLE IF NOT EXISTS manufacturers (
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(64) NOT NULL,
                    address VARCHAR(255) NOT NULL,
                    phone_number VARCHAR(12) NOT NULL,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    specialization VARCHAR(64) NOT NULL
                    );""";

            String productSql = """
                    CREATE TABLE IF NOT EXISTS products(
                    id BIGSERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    manufacturer_id BIGINT NOT NULL,
                    warehouse_id BIGINT NOT NULL,
                    date_of_manufacturing DATE NOT NULL,
                    date_of_placement_to_warehouse DATE NOT NULL,
                    cost NUMERIC(12, 2) NOT NULL CHECK (cost > 0),
                    discount NUMERIC(3, 2) NOT NULL CHECK (discount >= 0 AND discount <= 1),
                    CONSTRAINT manufacturer_fk
                       FOREIGN KEY (manufacturer_id)
                       REFERENCES manufacturers (id)
                       ON DELETE RESTRICT,
                    CONSTRAINT warehouse_fk
                        FOREIGN KEY (warehouse_id)
                        REFERENCES warehouses (id)
                        ON DELETE RESTRICT
                    );""";

            String orderSql = """
                    CREATE TABLE IF NOT EXISTS orders(
                    id BIGSERIAL PRIMARY KEY,
                    user_id BIGINT NOT NULL,
                    product_id BIGINT NOT NULL,
                    status VARCHAR(32) NOT NULL,
                    delivery_address VARCHAR(64) NOT NULL,
                    creation_date DATE NOT NULL,
                    last_delivering_day DATE NOT NULL,
                    CONSTRAINT user_fk
                        FOREIGN KEY (user_id)
                        REFERENCES users (id)
                        ON DELETE RESTRICT,
                    CONSTRAINT product_fk
                        FOREIGN KEY (product_id)
                        REFERENCES products (id)
                        ON DELETE RESTRICT
                    );""";

            creationStatement.addBatch(userSql);
            creationStatement.addBatch(warehouseSql);
            creationStatement.addBatch(manufacturerSql);
            creationStatement.addBatch(productSql);
            creationStatement.addBatch(orderSql);

            creationStatement.executeBatch();

            creationStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException("Can't init database tables!",e);
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
    }

}
