package model.repository;

import model.entity.User;
import model.enums.UserRole;
import model.exception.RepositoryException;
import model.repository.common.ContactableRepository;
import model.repository.common.ReferencableRepository;

import java.sql.Connection;
import java.util.List;

public interface UserRepository extends ContactableRepository<User>, ReferencableRepository {

    List<User> findAllByRole(UserRole role, Connection conn) throws RepositoryException;

}
