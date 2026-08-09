package infrastructure.config;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    public interface Operation<T>{
        T run() throws SQLException;
    }

    public interface VoidOperation{
        void run() throws SQLException;
    }

    public static<T> T complete(Connection conn, Operation<T> operation) throws SQLException{

        if (conn == null)
            throw new IllegalArgumentException(
                    "Connection can't be null!"
            );

        if (operation == null)
            throw new IllegalArgumentException(
                    "Operation can't be null!"
            );

        boolean autocommitStatus = conn.getAutoCommit();
        try{
            conn.setAutoCommit(false);
            T result = operation.run();
            conn.commit();
            return result;
        } catch (Exception e) {
            try{
                conn.rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
                throw new SQLException("Changes weren't rolled back!", e);
            }

            throw new SQLException("Changes were rolled back!", e);
        }finally {
            conn.setAutoCommit(autocommitStatus);
        }
    }


    public static void complete(Connection conn, VoidOperation operation) throws SQLException{
        complete(conn, () -> {
            operation.run();
            return null;
        });
    }
}
