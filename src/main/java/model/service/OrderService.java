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
import model.vo.FullAddress;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        Validator.validateNotNull(orderRepository, "Order repository");

        this.orderRepository = orderRepository;
    }

    public Id createOrder(Order order){
        Validator.validateNotNull(order, "Order");
        order.setDeliveryPeriod(7);
        Connection conn = ConnectionManager.getConnectionSingletone();
        return Transaction.complete(conn, () -> orderRepository.save(order, conn));
    }


    public void updateOrderInfo(int deliveryPeriod, FullAddress deliveryAddress, Id orderId){
        Validator.validateNotNull(deliveryAddress, "Full address");
        Validator.validateNotNull(orderId, "Order id");

        Connection conn = ConnectionManager.getConnectionSingletone();


        Order orderFromDb = orderRepository.findById(orderId, conn).orElseThrow(
                () -> new EntityNotFoundException("Unknown order id")
        );
        Order orderToUpdate = Order.loadFromDb(
                orderFromDb.getId(),
                orderFromDb.isDeleted(),
                orderFromDb.getUserId(),
                orderFromDb.getProductId(),
                deliveryPeriod,
                deliveryAddress,
                null,
                null
        );

        int affectedRows = Transaction.complete(conn, () ->
                orderRepository.update(orderToUpdate, orderId, conn)
        );

        if (affectedRows == 0)
            throw new ServiceException(
                    "Failed to update order"
            );
    }


    public void markAsDeleted(Id orderId){
        Validator.validateNotNull(orderId, "Order id");
        int affectedRows = orderRepository.setDeletionStatus(true, orderId,
                ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't mark order as deleted"
            );
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
