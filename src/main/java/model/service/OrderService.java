package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Order;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.OrderRepository;
import model.repository.ProductRepository;
import model.repository.UserRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
                        UserRepository userRepository){

        Validator.validateNotNull(orderRepository, "Order repository");
        Validator.validateNotNull(productRepository, "Product repository");
        Validator.validateNotNull(userRepository, "User repository");

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Id createOrder(Order order){
        Validator.validateNotNull(order, "Order");

        Connection conn = ConnectionManager.getConnectionSingletone();

        return Transaction.complete(conn, () -> {
            if (! userRepository.existsById(order.getUserId(), conn))
                throw new EntityNotFoundException(
                        "Unknown user"
                );

            if (!productRepository.existsById(order.getProductId(), conn))
                throw new EntityNotFoundException(
                        "Unknown product"
                );

            return Transaction.complete(conn, () -> orderRepository.save(order, conn));
        });
    }


    public void updateOrderInfo(Order order, Id orderId){
        Validator.validateNotNull(order, "Request dto");
        Validator.validateNotNull(orderId, "Order id");

        Connection conn = ConnectionManager.getConnectionSingletone();
        int affectedRows = orderRepository.update(order, orderId, conn);
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't update order information"
            );
    }


    public void markAsDeleted(Id orderId){
        Validator.validateNotNull(orderId, "Order id");
        orderRepository.setDeletionStatus(true, orderId, ConnectionManager.getConnectionSingletone());
    }

    public Order getOrderById(Id orderId){
        Validator.validateNotNull(orderId, "Order id");
        return orderRepository.findById(orderId, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown order")
        );
    }


    public List<Order> getOrdersByUserId(Id userId){
        Validator.validateNotNull(userId, "User id");
        return orderRepository.findAllByUserId(userId, ConnectionManager.getConnectionSingletone());
    }


    public List<Order> getOrdersByProductId(Id productId){
        Validator.validateNotNull(productId, "Product id");
        return orderRepository.findAllByProductId(productId, ConnectionManager.getConnectionSingletone());
    }


    public List<Order> getDeletedOrders(){
        return orderRepository.findAllByDeleteStatus(true, ConnectionManager.getConnectionSingletone());
    }

    public int cleanDeleted(){
        return orderRepository.remove(ConnectionManager.getConnectionSingletone());
    }
}
