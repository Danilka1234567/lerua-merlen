package model.repository;

import model.entity.Order;
import model.repository.common.ExtendedRepository;

import java.sql.Connection;
import java.util.List;

public interface OrderRepository extends ExtendedRepository<Order> {

    List<Order> findAllByProductId(Long productId, Connection conn);
    List<Order> findAllByUserId(Long userId, Connection conn);

}
