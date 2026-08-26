package model.repository;

import model.entity.User;
import model.enums.UserRole;
import model.exception.ForeignKeyViolationException;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.exception.UniqueViolationException;
import model.vo.Email;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository{

    Id save(User entity, Connection conn) throws GeneratedKeysException, RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    int update(User entity, Id id, Connection conn) throws RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    List<User> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<User> findById(Id id, Connection conn) throws RepositoryException;
    List<User> findAllByRole(UserRole role, Connection conn) throws RepositoryException;
    Optional<User> findByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    Optional<User> findByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    boolean existsById(Id id, Connection conn) throws RepositoryException;
}
