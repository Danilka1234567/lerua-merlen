package model.service.shared;

import model.exception.ForeignKeyViolationException;
import model.exception.ServiceException;
import model.exception.UniqueViolationException;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    public interface Operation<T>{
        T run() throws SQLException;
    }

    public interface VoidOperation{
        void run() throws SQLException;
    }

    public static<T> T complete(Connection conn, Operation<T> operation) throws ServiceException,
                ForeignKeyViolationException, UniqueViolationException{

        if (conn == null)
            throw new IllegalArgumentException(
                    "Connection can't be null!"
            );

        if (operation == null)
            throw new IllegalArgumentException(
                    "Operation can't be null!"
            );

        boolean autocommitStatus;
        try{
            autocommitStatus = conn.getAutoCommit();
        } catch (SQLException e) {
            throw new ServiceException("Major connection exception", e);
        }

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
                throw new ServiceException("Changes weren't rolled back, but should be!", e);
            }

            if (e instanceof UniqueViolationException ue)
                throw ue;
            else if (e instanceof ForeignKeyViolationException fe)
                throw fe;

            throw new ServiceException("Changes were rolled back!", e);
        }finally {

            try{
                conn.setAutoCommit(autocommitStatus);
            } catch (SQLException e) {
                throw new ServiceException("Major connection exception", e);
            }

        }
    }


    public static void complete(Connection conn, VoidOperation operation) throws ServiceException,
            ForeignKeyViolationException, UniqueViolationException{
        complete(conn, () -> {
            operation.run();
            return null;
        });
    }
}
