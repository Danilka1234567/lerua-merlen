package infrastructure.repositoryImpl;

import infrastructure.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.ContactableRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.rsmapper.UserMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.User;
import model.enums.UserRole;
import model.repository.UserRepository;
import model.vo.Email;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class UserRepositoryImpl extends ContactableRepositoryImpl<User> implements UserRepository {

    private static final RsMapper<User> mapper = new UserMapper();

    private static final String findByRoleSql = ResourceReader.read(
            "sql/dql/user/select_by_role.sql"
    );

    private static final String existsByIdSql = ResourceReader.read(
            "sql/dql/user/exists_by_id.sql"
    );

    private static final String findByPhoneNumberSql = ResourceReader.read(
            "sql/dql/user/select_by_phone_number.sql"
    );

    private static final String findByEmailSql = ResourceReader.read(
            "sql/dql/user/select_by_email.sql"
    );

    private static final String findAllByRegDateSql = ResourceReader.read(
            "sql/dql/user/select_by_reg_date.sql"
    );

    private static final String findAllBetweenRegDatesSql = ResourceReader.read(
            "sql/dql/user/select_between_reg_dates.sql"
    );

    private static final String updateSql = ResourceReader.read(
            "sql/dml/user/update.sql"
    );

    private static final String findAllByDeleteStatusSql = ResourceReader.read(
            "sql/dql/user/select_by_delete_status.sql"
    );

    private static final String findByIdSql = ResourceReader.read(
            "sql/dql/user/select_by_id.sql"
    );

    private static final String saveSql = ResourceReader.read(
            "sql/dml/user/insert.sql"
    );

    private static final String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/user/update_deletion_status.sql"
    );

    private static final String removeSql = ResourceReader.read(
            "sql/dml/user/delete.sql"
    );

    @Override
    protected RsMapper<User> getMapper() {
        return mapper;
    }

    @Override
    protected String getFindByPhoneNumberSql() {
        return findByPhoneNumberSql;
    }

    @Override
    protected String getFindByEmailSql() {
        return findByEmailSql;
    }

    @Override
    protected String getFindAllByRegDateSql() {
        return findAllByRegDateSql;
    }

    @Override
    protected String getFindAllBetweenRegDateSql() {
        return findAllBetweenRegDatesSql;
    }

    @Override
    protected String getUpdateSql() {
        return updateSql;
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return findAllByDeleteStatusSql;
    }

    @Override
    protected String getFindByIdSql() {
        return findByIdSql;
    }

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
    protected void fillFindAllByRegDatePstmt(PreparedStatement statement, LocalDate date) throws SQLException {
        statement.setDate(1, Date.valueOf(date));
    }

    @Override
    protected void fillFindAllBetweenRegDatePstmt(PreparedStatement statement, LocalDate start, LocalDate end) throws SQLException {
        statement.setDate(1, Date.valueOf(start));
        statement.setDate(2, Date.valueOf(end));
    }

    @Override
    protected void fillUpdatePstmt(PreparedStatement statement, User entity, Id id) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(4, entity.getPassword().getValue());
        statement.setString(5, entity.getRole().name());
        statement.setLong(6, id.getValue());
    }

    @Override
    protected void fillFindAllByDeleteStatusPstmt(PreparedStatement statement, boolean status) throws SQLException {
        statement.setBoolean(1, status);
    }

    @Override
    protected void fillFindByIdPstmt(PreparedStatement statement, Id id) throws SQLException {
        statement.setLong(1, id.getValue());
    }

    @Override
    protected void fillSaveStatement(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(4, entity.getPassword().getValue());
        statement.setString(5, entity.getRole().name());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setLong(1, id.getValue());
    }

    @Override
    protected void fillFindByPhoneNumberPstmt(PreparedStatement statement, PhoneNumber phoneNumber) throws SQLException {
        statement.setString(1, phoneNumber.getValue());
    }

    @Override
    protected void fillFindByEmailPstmt(PreparedStatement statement, Email email) throws SQLException {
        statement.setString(1, email.getValue());
    }

    @Override
    public List<User> findAllByRole(UserRole role, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findByRoleSql)){
            statement.setString(1, role.name());

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find users by role!", e
            );
        }
    }

    @Override
    public boolean existsById(Id id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, existsByIdSql);
    }
}