package infrastructure.repositoryImpl.rsmapper;

import model.entity.Session;
import model.entity.User;
import model.enums.UserRole;
import model.vo.ContactInfo;
import model.vo.Email;
import model.vo.Password;
import model.vo.PhoneNumber;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SessionMapper implements RsMapper<Session> {

    @Override
    public Session mapRsToEntity(ResultSet rs) throws SQLException {
        return Session.loadFromDb(
                rs.getLong("session_id"),
                rs.getBoolean("session_is_deleted"),
                rs.getLong("user_id"),
                rs.getTimestamp("expiration_date").toLocalDateTime(),
                User.loadFromDb(
                        rs.getLong("user_id"),
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
