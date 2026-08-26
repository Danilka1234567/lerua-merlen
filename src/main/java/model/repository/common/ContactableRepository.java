package model.repository.common;

import model.entity.abstr.ContactableEntity;
import model.exception.RepositoryException;
import model.vo.Email;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.util.Optional;

public interface ContactableRepository<T extends ContactableEntity> extends ExtendedRepository<T> {

    Optional<T> findByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    Optional<T> findByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
}
