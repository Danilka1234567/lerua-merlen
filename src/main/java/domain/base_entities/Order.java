package domain.base_entities;

import domain.abstract_entities.BaseEntity;
import utils.Validator;

import java.time.LocalDate;

public class Order extends BaseEntity {

    private Long userId;
    private Long productId;
    private String deliveryAddress;
    private LocalDate dateOfCreation;
    private int deliveryPeriod;


    public Order(Long userId, Long productId, String deliveryAddress, LocalDate dateOfCreation, int deliveryPeriod) {
        setUserId(userId);
        setProductId(productId);
        setDeliveryAddress(deliveryAddress);
        setDateOfCreation(dateOfCreation);
        setDeliveryPeriod(deliveryPeriod);
    }

    public Order(Long id, Long userId, Long productId, String deliveryAddress, LocalDate dateOfCreation, int deliveryPeriod) {
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {

        if (! Validator.isValidAddress(deliveryAddress))
            throw new IllegalArgumentException(
                    "Invalid address"
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
