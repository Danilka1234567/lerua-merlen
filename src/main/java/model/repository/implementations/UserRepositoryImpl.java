package model.repository.implementations;

import infrastructure.config.DataBaseConfig;
import infrastructure.config.Transaction;
import infrastructure.exceptions.repository.RepositoryException;

import model.entities.User;
import model.repository.interfaces.UserRepository;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_EMAIL_SQL =
            "SELECT id, name, surname, phone_number, email FROM users WHERE email = ?";

    private static final String EXISTS_BY_EMAIL_SQL =
            "SELECT EXISTS(SELECT 1 FROM users WHERE email = ? LIMIT 1)";

    private static final String SAVE_SQL =
            "INSERT INTO users (name, surname, phone_number, email) VALUES (?, ?, ?, ?)";

    private static final String FIND_BY_ID_SQL =
            "SELECT id, name, surname, phone_number, email FROM users WHERE id = ?";

    private static final String UPDATE_SQL =
            "UPDATE users SET name = ?, surname = ?, phone_number = ?, email = ? WHERE id = ?";

    private static final String REMOVE_SQL =
            "DELETE FROM users WHERE id = ?";

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

    @Override
    public long save(User obj) {

        if (obj == null)
            throw new IllegalArgumentException(
                    "Can't save user: user is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {
               try(PreparedStatement statement = conn.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){

                   statement.setString(1, obj.getName());
                   statement.setString(2, obj.getSurname());
                   statement.setString(3, obj.getPhoneNumber().getValue());
                   statement.setString(4, obj.getEmail().getValue());

                   statement.executeUpdate();
                   try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                       if (! generatedKeys.next())
                           throw new SQLException("Can't receive generated id");

                       return generatedKeys.getLong(1);
                   }
               }
            });

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't save user with email: " + obj.getEmail().getValue(), e
            );
        }
    }

    @Override
    public Optional<User> findById(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find user by id: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_ID_SQL)){

            statement.setLong(1, id);

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                User user = mapResult(rs);
                return Optional.of(user);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find user by id: " + id, e
            );
        }

    }

    @Override
    public int update(User obj) {
        if (obj == null)
            throw new IllegalArgumentException(
                    "Can't update user: user is null"
            );

        if (obj.getId() == null)
            throw new IllegalArgumentException(
                    "Can't update user: user's id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)){

                    statement.setString(1, obj.getName());
                    statement.setString(2, obj.getSurname());
                    statement.setString(3, obj.getPhoneNumber().getValue());
                    statement.setString(4, obj.getEmail().getValue());
                    statement.setLong(5, obj.getId());

                    int affectedRows = statement.executeUpdate();

                    if (affectedRows == 0)
                        throw new SQLException(
                                "Unknown user"
                        );

                    return affectedRows;
                }
            });
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't update user with id: " + obj.getId(), e
            );
        }
    }

    @Override
    public void remove(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                    "Can't remove user: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            Transaction.execute(conn, () -> {
               try(PreparedStatement statement = conn.prepareStatement(REMOVE_SQL)){

                   statement.setLong(1, id);
                   int affectedRows = statement.executeUpdate();

                   if (affectedRows == 0)
                       throw new SQLException(
                               "Unknown id"
                       );

               }
            });

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't remove user with id " + id, e
            );
        }
    }

    private User mapResult(ResultSet rs) throws SQLException{
        return new User(
                rs.getLong("id"),
                new Email(rs.getString("email")),
                new PhoneNumber(rs.getString("phone_number")),
                rs.getString("name"),
                rs.getString("surname")
        );
    }
}
