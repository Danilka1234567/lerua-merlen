package infrastructure.repositoryImpl;

import infrastructure.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.BaseRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.rsmapper.SessionMapper;
import infrastructure.utils.ResourceReader;
import model.entity.Session;
import model.repository.SessionRepository;

import java.sql.*;
import java.util.Optional;

public class SessionRepositoryImpl extends BaseRepositoryImpl<Session> implements SessionRepository {


    private static final SessionMapper mapper = new SessionMapper();
    private static final String findByUserIdSql = ResourceReader.read("sql/dql/session/select_by_user_id.sql");

    @Override
    protected String getSaveSql() {
        return ResourceReader.read("sql/dml/session/insert.sql");
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return ResourceReader.read("sql/dml/session/update_deletion_status.sql");
    }

    @Override
    protected String getRemoveSql() {
        return ResourceReader.read("sql/dml/session/delete.sql");
    }

    @Override
    protected void fillSaveStatement(PreparedStatement statement, Session entity) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setTimestamp(2, Timestamp.valueOf(entity.getExpirationDate()));
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Long id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id);
    }

    @Override
    public Optional<Session> findByUserId(Long userId, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(findByUserIdSql)){
            statement.setLong(1, userId);

            try(ResultSet rs = statement.executeQuery()){
                if (! rs.next())
                    return Optional.empty();

                return Optional.of(mapper.mapRsToEntity(rs));
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find session by user id", e
            );
        }
    }
}
