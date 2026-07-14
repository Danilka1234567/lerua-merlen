package model.repository.implementations.base;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.base.BaseEntity;
import model.repository.implementations.crud.CrudRepositoryImpl;
import model.repository.interfaces.BaseRepository;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseRepositoryImpl<T extends BaseEntity> extends CrudRepositoryImpl<T> implements BaseRepository<T> {

    protected abstract String getExistsByIdSql();

    protected void setExistsByIdValues(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Can't check entity existence by id: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(getExistsByIdSql())){
            setExistsByIdValues(statement, id);

            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check entity existence by id: " + id, e
            );
        }
    }
}
