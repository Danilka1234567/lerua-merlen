package model.repository.implementations;

import infrastructure.config.DataBaseConfig;
import infrastructure.config.Transaction;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.abstract_entities.BaseEntity;
import model.repository.interfaces.CrudRepository;

import java.sql.*;
import java.util.Optional;

public abstract class CrudRepositoryImpl<T extends BaseEntity> implements CrudRepository<T> {

    protected abstract String getSaveSql();
    protected abstract String getFindByIdSql();
    protected abstract String getUpdateSql();
    protected abstract String getRemoveSql();

    protected abstract void setSaveValues(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void setFindByIdValues(PreparedStatement statement, Long id) throws SQLException;
    protected abstract void setUpdateValues(PreparedStatement statement, T entity) throws SQLException;
    protected abstract void setRemoveValues(PreparedStatement statement, Long id) throws SQLException;

    protected abstract T mapResult(ResultSet rs) throws SQLException;

    @Override
    public long save(T entity) {
        if (entity == null)
            throw new IllegalArgumentException(
                    "Can't save entity: entity is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(getSaveSql(), Statement.RETURN_GENERATED_KEYS)){
                    setSaveValues(statement, entity);
                    statement.executeUpdate();
                    try(ResultSet rs = statement.getGeneratedKeys()){
                        if (! rs.next())
                            throw new SQLException(
                                    "Can't receive generated keys"
                            );
                        return rs.getLong(1);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Can't save entity: " + entity.getClass().getSimpleName(), e
            );
        }
    }

    @Override
    public Optional<T> findById(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find entity: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(getFindByIdSql())){

            setFindByIdValues(statement, id);
            ResultSet rs = statement.executeQuery();

            if (! rs.next())
                return Optional.empty();

            T entity = mapResult(rs);
            return Optional.of(entity);
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find entity by id: " + id, e
            );
        }
    }

    @Override
    public int update(T entity) {

        if (entity == null)
            throw new IllegalArgumentException(
                    "Can't update entity: entity is null"
            );

        if (entity.getId() == null)
            throw new IllegalArgumentException(
                    "Can't update entity: entity's id is null"
            );


        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {

                    try(PreparedStatement statement = conn.prepareStatement(getUpdateSql())){
                        setUpdateValues(statement, entity);

                        int affectedRows = statement.executeUpdate();
                        if (affectedRows == 0){
                            throw new SQLException(
                                    "Unknown entity"
                            );
                        }

                        return affectedRows;
                    }
                }
            );

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't update entity with id: " + entity.getId(), e
            );
        }
    }

    @Override
    public void remove(Long id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Can't remove entity: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(getRemoveSql())){
                    setRemoveValues(statement, id);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0)
                        throw new SQLException(
                                "Unknown id: " + id
                        );


                }
            });
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't remove entity with id: " + id, e
            );
        }
    }
}
