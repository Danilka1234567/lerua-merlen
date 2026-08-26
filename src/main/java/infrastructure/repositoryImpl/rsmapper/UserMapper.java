package infrastructure.repositoryImpl.rsmapper;

import model.entity.User;
import model.enums.UserRole;
import model.vo.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RsMapper<User> {

    @Override
    public User mapRsToEntity(ResultSet rs) throws SQLException {
        return User.loadFromDb(
                new Id(rs.getLong("id")),
                rs.getBoolean("is_deleted"),
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
