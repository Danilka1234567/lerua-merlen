package infrastructure.repositoryImpl.abstr;

import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.entity.abstr.BaseEntity;
import model.vo.Id;

import java.sql.*;

public abstract class BaseRepositoryImpl<T extends BaseEntity> {

    protected abstract String getSaveSql();
    protected abstract String getSetDeletionStatusSql();
    protected abstract String getRemoveSql();

    protected abstract void fillSaveStatement(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void fillSetDeletionStatusStatement(PreparedStatement statement,
                                                           Id id, boolean deletionStatus) throws SQLException;


    public Id save(T entity, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getSaveSql(), Statement.RETURN_GENERATED_KEYS)){
            fillSaveStatement(statement, entity);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("your query affected 0 rows");
            }

            try(ResultSet rs = statement.getGeneratedKeys()){

                if (! rs.next())
                    throw new SQLException("there are no generated keys!");

                return new Id(rs.getLong(1));
            }catch (SQLException e){
                throw new GeneratedKeysException(
                        "Can't receive generated key from DB", e
                );
            }

        }catch (SQLException e){
            throw new RepositoryException("Can't save entity into DB!", e);
        }
    }


    public int setDeletionStatus(boolean status, Id id, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getSetDeletionStatusSql())){
            fillSetDeletionStatusStatement(statement, id, status);

            return statement.executeUpdate();
        }catch (SQLException e){
            throw new RepositoryException("Can't set deletion status in DB!", e);
        }
    }


    public int remove(Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getRemoveSql())){
            return statement.executeUpdate();

        }catch (SQLException e){
            throw new RepositoryException("Can't remove entities from DB!", e);
        }
    }

}