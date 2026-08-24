package model.repository;

import model.entity.Order;
import model.exception.RepositoryException;
import model.repository.common.ExtendedRepository;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public interface OrderRepository extends ExtendedRepository<Order> {

    List<Order> findAllByProductId(Id productId, Connection conn) throws RepositoryException;
    List<Order> findAllByUserId(Id userId, Connection conn) throws RepositoryException;

}
