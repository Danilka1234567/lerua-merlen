package model.repository.implementations;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;

import model.entities.User;
import model.repository.interfaces.UserRepository;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

    private static final String FIND_BY_EMAIL_SQL =
            "SELECT id, name, surname, phone_number, email FROM users WHERE email = ?";

    private static final String EXISTS_BY_EMAIL_SQL =
            "SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)";


    @Override
    protected String getSaveSql() {
        return "INSERT INTO users (name, surname, phone_number, email) VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getFindByIdSql() {
        return "SELECT id, name, surname, phone_number, email FROM users WHERE id = ?";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE users SET name = ?, surname = ?, phone_number = ?, email = ? WHERE id = ?";
    }

    @Override
    protected String getRemoveSql() {
        return "DELETE FROM users WHERE id = ?";
    }

    @Override
    protected void setSaveValues(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
    }

    @Override
    protected void setFindByIdValues(PreparedStatement statement, Long id) throws SQLException{
        statement.setLong(1, id);
    }

    @Override
    protected void setUpdateValues(PreparedStatement statement, User entity) throws SQLException{
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
        statement.setLong(5, entity.getId());
    }

    @Override
    protected void setRemoveValues(PreparedStatement statement, Long id) throws SQLException{
        statement.setLong(1, id);
    }

    @Override
    protected User mapResult(ResultSet rs) throws SQLException{
        return new User(
                rs.getLong("id"),
                new Email(rs.getString("email")),
                new PhoneNumber(rs.getString("phone_number")),
                rs.getString("name"),
                rs.getString("surname")
        );
    }

    @Override
    public Optional<User> findByEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Can't find user by email: email is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_EMAIL_SQL)){

            statement.setString(1, email.getValue());

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                User user = mapResult(rs);
                return Optional.of(user);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find user by email: " + email, e
            );
        }
    }

    @Override
    public boolean existsByEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Can't check user existence by email: email is null"
            );

        try(Connection connection = DataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(EXISTS_BY_EMAIL_SQL)){

            statement.setString(1, email.getValue());
            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check user existence by email: " + email, e
            );
        }
    }
}
