package infrastructure.repositoryImpl.abstr;

import infrastructure.exception.RepositoryException;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import model.entity.abstr.ExtendedEntity;
import model.repository.common.ExtendedRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public abstract class ExtendedRepositoryImpl<T extends ExtendedEntity> extends BaseRepositoryImpl<T>
        implements ExtendedRepository<T> {


    protected abstract RsMapper<T> getMapper();

    protected abstract String getFindAllByRegDateSql();
    protected abstract String getFindAllBetweenRegDateSql();
    protected abstract String getUpdateSql();
    protected abstract String getFindAllByDeleteStatusSql();
    protected abstract String getFindByIdSql();


    protected abstract void fillFindAllByRegDatePstmt(PreparedStatement statement, Date date) throws SQLException;
    protected abstract void fillFindAllBetweenRegDatePstmt(PreparedStatement statement,
                                                           Date start, Date end) throws SQLException;
    protected abstract void fillUpdatePstmt(PreparedStatement statement, T entity) throws  SQLException;
    protected abstract void fillFindAllByDeleteStatusPstmt(PreparedStatement statement,
                                                           boolean status) throws  SQLException;
    protected abstract void fillFindByIdPstmt(PreparedStatement statement, Long id) throws SQLException;


    protected final List<T> mapRsToList(ResultSet rs) throws SQLException{
        List<T> entities = new ArrayList<>();
        RsMapper<T> mapper = getMapper();
        while (rs.next())
            entities.add(mapper.mapRsToEntity(rs));
        return entities;
    }

    @Override
    public List<T> findAllByRegDate(Date date, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindAllByRegDateSql())){
            fillFindAllByRegDatePstmt(statement, date);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find all entities by registration date!", e
            );
        }
    }

    @Override
    public List<T> findAllBetweenRegDate(Date start, Date end, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindAllBetweenRegDateSql())){
            fillFindAllBetweenRegDatePstmt(statement, start, end);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find all entities registered between two dates!", e
            );
        }
    }

    @Override
    public int update(T entity, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getUpdateSql())){
            fillUpdatePstmt(statement, entity);
            return statement.executeUpdate();
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't update entity!", e
            );
        }
    }

    @Override
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

    @Override
    public Optional<T> findById(Long id, Connection conn) {

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
