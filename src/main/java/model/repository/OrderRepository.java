package model.repository;

import model.entity.Order;
import model.exception.ForeignKeyViolationException;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.exception.UniqueViolationException;
import model.vo.Id;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Id save(Order entity, Connection conn) throws GeneratedKeysException, RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    int update(Order entity, Id id, Connection conn) throws RepositoryException, ForeignKeyViolationException, UniqueViolationException;
    List<Order> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<Order> findById(Id id, Connection conn) throws RepositoryException;
    List<Order> findAllByProductId(Id productId, Connection conn) throws RepositoryException;
    List<Order> findAllByUserId(Id userId, Connection conn) throws RepositoryException;

}
