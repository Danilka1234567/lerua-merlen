package model.dto.request;

import model.entities.Order;
import model.valueobjects.Address;
import utils.enums.OrderStatus;

import java.time.LocalDate;

public class OrderRequestDto{

    private Long userId;
    private Long productId;
    private Address deliveryAddress;
    private LocalDate dateOfCreation;
    private int deliveryPeriod;
    private OrderStatus status;

    public OrderRequestDto(Long userId, Long productId, Address deliveryAddress, LocalDate dateOfCreation, int deliveryPeriod, OrderStatus status) {
        setUserId(userId);
        setProductId(productId);
        setDeliveryAddress(deliveryAddress);
        setDateOfCreation(dateOfCreation);
        setDeliveryPeriod(deliveryPeriod);
        setStatus(status);
    }

    public void setUserId(Long userId) {

        if (userId == null || userId < 0)
            throw new IllegalArgumentException(
                    "Can't assign userId to order dto: id is null or id < 0"
            );
        this.userId = userId;
    }

    public void setProductId(Long productId) {
        if (productId == null || productId < 0)
            throw new IllegalArgumentException(
                    "Can't assign productId to order dto: id is null or id < 0"
            );
        this.productId = productId;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        if (deliveryAddress == null)
            throw new IllegalArgumentException(
                    "Can't assign deliveryAddress to order dto: address is null"
            );
        this.deliveryAddress = deliveryAddress;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        if (dateOfCreation == null)
            throw new IllegalArgumentException(
                    "Can't assign dateOfCreation to order dto: date is null"
            );
        this.dateOfCreation = dateOfCreation;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        if (deliveryPeriod < 0)
            throw new IllegalArgumentException(
                    "Can't assign deliveryPeriod to order dto: period must be bigger than 0"
            );
        this.deliveryPeriod = deliveryPeriod;
    }

    public void setStatus(OrderStatus status) {
        if (status == null)
            throw new IllegalArgumentException(
                    "Can't assign status to order dto: status is null"
            );
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
