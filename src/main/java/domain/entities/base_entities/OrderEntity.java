package domain.entities.base_entities;

import domain.entities.abstract_entities.BaseEntity;
import domain.value_objects.Address;

import java.time.LocalDate;

public class OrderEntity extends BaseEntity {

    private Long userId;
    private Long productId;
    private Address deliveryAddress;
    private LocalDate dateOfCreation;
    private int deliveryPeriod;


    public OrderEntity(Long userId, Long productId, Address deliveryAddress, LocalDate dateOfCreation, int deliveryPeriod) {
        setUserId(userId);
        setProductId(productId);
        setDeliveryAddress(deliveryAddress);
        setDateOfCreation(dateOfCreation);
        setDeliveryPeriod(deliveryPeriod);
    }

    public OrderEntity(Long id, Long userId, Long productId, Address deliveryAddress, LocalDate dateOfCreation, int deliveryPeriod) {
        setId(id);
        setUserId(userId);
        setProductId(productId);
        setDeliveryAddress(deliveryAddress);
        setDateOfCreation(dateOfCreation);
        setDeliveryPeriod(deliveryPeriod);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {

        if (userId == null)
            throw new IllegalArgumentException(
                    "User id can't be null"
            );
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {

        if (productId == null)
            throw new IllegalArgumentException(
                    "Product id can't be null"
            );

        this.productId = productId;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {

        if (deliveryAddress == null)
            throw new IllegalArgumentException(
                    "Delivery address can't be null"
            );

        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {

        if (deliveryPeriod < 1)
            throw new IllegalArgumentException(
                    "Delivery period can't be less than 1 day"
            );

        this.deliveryPeriod = deliveryPeriod;
    }
}
