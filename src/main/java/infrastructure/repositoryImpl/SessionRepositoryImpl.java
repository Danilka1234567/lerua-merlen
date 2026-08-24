package infrastructure.repositoryImpl;

import model.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.BaseRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.SessionMapper;
import infrastructure.utils.ResourceReader;
import model.entity.Session;
import model.repository.SessionRepository;
import model.vo.Id;

import java.sql.*;
import java.util.Optional;

public class SessionRepositoryImpl extends BaseRepositoryImpl<Session> implements SessionRepository {

    private static final SessionMapper mapper = new SessionMapper();

    private static final String findByUserIdSql = ResourceReader.read(
            "sql/dql/session/select_by_user_id.sql"
    );

    private static final String saveSql = ResourceReader.read(
            "sql/dml/session/insert.sql"
    );

    private static final String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/session/update_deletion_status.sql"
    );

    private static final String removeSql = ResourceReader.read(
            "sql/dml/session/delete.sql"
    );

    @Override
    protected String getSaveSql() {
        return saveSql;
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return setDeletionStatusSql;
    }

    @Override
    protected String getRemoveSql() {
        return removeSql;
    }

    @Override
    protected void fillSaveStatement(PreparedStatement statement, Session entity) throws SQLException {
        statement.setLong(1, entity.getUserId().getValue());
        statement.setTimestamp(2, Timestamp.valueOf(entity.getExpirationDate()));
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id.getValue());
    }

    @Override
    public Optional<Session> findByUserId(Id userId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findByUserIdSql)){
            statement.setLong(1, userId.getValue());

            try(ResultSet rs = statement.executeQuery()){
                if (!rs.next())
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