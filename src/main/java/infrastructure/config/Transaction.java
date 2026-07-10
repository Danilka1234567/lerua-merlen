package infrastructure.config;

import infrastructure.exceptions.repository.RepositoryException;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    public static <T> T execute(Connection conn, Operation<T> operation) throws SQLException{
        boolean commitStatus = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try{
            T result = operation.run();
            conn.commit();
            return result;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(commitStatus);
        }

    }

    public static void execute(Connection conn, VoidOperation operation) throws SQLException{
        execute(conn, () -> {
            operation.run();
            return null;
        });
    }

    @FunctionalInterface
    public interface VoidOperation{
        void run() throws SQLException;
    }

    @FunctionalInterface
    public interface Operation<T>{
        T run() throws SQLException;
    }
}
