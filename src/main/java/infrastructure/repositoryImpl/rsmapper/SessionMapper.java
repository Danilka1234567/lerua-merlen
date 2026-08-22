package infrastructure.repositoryImpl.rsmapper;

import model.entity.Session;
import model.entity.User;
import model.enums.UserRole;
import model.vo.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper implements RsMapper<Session> {

    @Override
    public Session mapRsToEntity(ResultSet rs) throws SQLException {
        return Session.loadFromDb(
                new Id(rs.getLong("session_id")),
                rs.getBoolean("session_is_deleted"),
                new Id(rs.getLong("user_id")),
                rs.getTimestamp("expiration_date").toLocalDateTime(),
                User.loadFromDb(
                        new Id(rs.getLong("user_id")),
                        rs.getBoolean("user_is_deleted"),
                        rs.getDate("registration_date").toLocalDate(),
                        new ContactInfo(
                                new PhoneNumber(rs.getString("phone_number")),
                                new Email(rs.getString("email"))
                        ),
                        rs.getString("name"),
                        new Password(rs.getString("password")),
                        UserRole.valueOf(rs.getString("role"))
                )
        );
    }
}
