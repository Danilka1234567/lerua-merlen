package infrastructure.repositoryImpl.abstr;

import model.exception.RepositoryException;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import model.vo.Id;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ExtendedRepositoryImpl<T extends ExtendedEntity> extends BaseRepositoryImpl<T> {


    protected abstract RsMapper<T> getMapper();

    protected abstract String getUpdateSql();
    protected abstract String getFindAllByDeleteStatusSql();
    protected abstract String getFindByIdSql();

    protected abstract void fillUpdatePstmt(PreparedStatement statement, T entity, Id id) throws  SQLException;
    protected abstract void fillFindAllByDeleteStatusPstmt(PreparedStatement statement,
                                                           boolean status) throws  SQLException;
    protected abstract void fillFindByIdPstmt(PreparedStatement statement, Id id) throws SQLException;


    protected final List<T> mapRsToList(ResultSet rs) throws SQLException{
        List<T> entities = new ArrayList<>();
        RsMapper<T> mapper = getMapper();
        while (rs.next())
            entities.add(mapper.mapRsToEntity(rs));
        return entities;
    }


    public int update(T entity, Id id, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getUpdateSql())){
            fillUpdatePstmt(statement, entity, id);
            return statement.executeUpdate();
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't update entity!", e
            );
        }
    }


    public List<T> findAllByDeleteStatus(boolean status, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getFindAllByDeleteStatusSql())){
            fillFindAllByDeleteStatusPstmt(statement, status);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find all entities by deletion status!", e
            );
        }
    }


    public Optional<T> findById(Id id, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindByIdSql())){
            fillFindByIdPstmt(statement, id);

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                return Optional.of(getMapper().mapRsToEntity(rs));
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find entity by id!", e
            );
        }

    }
}
