package model.entity;

import model.entity.abstr.ExtendedEntity;
import model.vo.FullAddress;

import java.time.LocalDate;

public class Order extends ExtendedEntity {

    private Long userId;
    private Long productId;
    private int deliveryPeriod;
    private FullAddress deliveryAddress;

    private User user;
    private Product product;

    public Order(Long userId, Long productId, int deliveryPeriod, FullAddress deliveryAddress) {
        setUserId(userId);
        setProductId(productId);
        setDeliveryPeriod(deliveryPeriod);
        setDeliveryAddress(deliveryAddress);
    }

    public Order(Long id, boolean isDeleted, LocalDate registrationDate, Long userId,
                 Long productId, int deliveryPeriod, FullAddress deliveryAddress, Product product, User user) {
        super(id, isDeleted, registrationDate);
        setUserId(userId);
        setProductId(productId);
        setDeliveryPeriod(deliveryPeriod);
        setDeliveryAddress(deliveryAddress);
        setProduct(product);
        setUser(user);
    }


    private void setUserId(Long userId) {
        if(userId == null)
            throw new IllegalArgumentException(
                    "userId can't be null"
            );

        this.userId = userId;
    }

    private void setProductId(Long productId) {
                this.productId = productId;
    }

    private void setDeliveryPeriod(int deliveryPeriod) {
        if (deliveryPeriod <= 0)
            throw new IllegalArgumentException(
                    "deliveryPeriod can't be less than zero or equal to it"
            );

        this.deliveryPeriod = deliveryPeriod;
    }

    private void setDeliveryAddress(FullAddress deliveryAddress) {
        if (deliveryAddress == null)
            throw new IllegalArgumentException(
                    "deliveryAddress can't be null"
            );

        this.deliveryAddress = deliveryAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public FullAddress getDeliveryAddress() {
        return deliveryAddress;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    private void setProduct(Product product) {
        this.product = product;
    }
}
