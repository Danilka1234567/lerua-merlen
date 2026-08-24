package infrastructure.repositoryImpl.abstr;

import model.exception.RepositoryException;
import model.entity.abstr.ContactableEntity;
import model.repository.common.ContactableRepository;
import model.vo.Email;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public abstract class ContactableRepositoryImpl<T extends ContactableEntity> extends ExtendedRepositoryImpl<T>
                                        implements ContactableRepository<T> {

    protected abstract String getFindByPhoneNumberSql();
    protected abstract String getFindByEmailSql();

    protected abstract void fillFindByPhoneNumberPstmt(PreparedStatement statement,
                                                       PhoneNumber phoneNumber) throws SQLException;
    protected abstract void fillFindByEmailPstmt(PreparedStatement statement,
                                                 Email email) throws SQLException;

    @Override
    public Optional<T> findByPhoneNumber(PhoneNumber phoneNumber, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getFindByPhoneNumberSql())){
                fillFindByPhoneNumberPstmt(statement, phoneNumber);
                try(ResultSet rs = statement.executeQuery()){
                    if (! rs.next())
                        return Optional.empty();

                    return Optional.of(getMapper().mapRsToEntity(rs));
                }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find entity by phone number", e
            );
        }
    }

    @Override
    public Optional<T> findByEmail(Email email, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getFindByEmailSql())){
            fillFindByEmailPstmt(statement, email);
            try(ResultSet rs = statement.executeQuery()){
                if (! rs.next())
                    return Optional.empty();

                return Optional.of(getMapper().mapRsToEntity(rs));
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find entity by email" , e
            );
        }
    }
}
