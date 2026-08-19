package infrastructure.repositoryImpl.shared;

import infrastructure.exception.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExistenceChecker {


    public static boolean checkExistenceById(Connection connection, Long id, String sqlQuery){
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            statement.setLong(1, id);

            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to check entity existence in db!", e
            );
        }
    }

}
