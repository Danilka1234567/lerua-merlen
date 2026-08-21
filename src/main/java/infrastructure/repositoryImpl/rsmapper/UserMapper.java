package infrastructure.repositoryImpl.rsmapper;

import model.entity.User;
import model.enums.UserRole;
import model.vo.ContactInfo;
import model.vo.Email;
import model.vo.Password;
import model.vo.PhoneNumber;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RsMapper<User> {

    @Override
    public User mapRsToEntity(ResultSet rs) throws SQLException {
        return User.loadFromDb(
                rs.getLong("id"),
                rs.getBoolean("is_deleted"),
                rs.getDate("registration_date").toLocalDate(),
                new ContactInfo(
                        new PhoneNumber(rs.getString("phone_number")),
                        new Email(rs.getString("email"))
                ),
                rs.getString("name"),
                new Password(rs.getString("password")),
                UserRole.valueOf(rs.getString("role"))
        );
    }
}
