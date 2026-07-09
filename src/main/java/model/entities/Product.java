package model.entities;

import model.entities.abstract_entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product extends BaseEntity {

    private final static double MAX_DISCOUNT = 0.75;

    private String name;
    private Long manufacturerId;
    private Long warehouseId;
    private LocalDate dateOfManufacturing;
    private LocalDate dateOfPlacementToWarehouse;
    private BigDecimal price;
    private BigDecimal discount;

    public Product(String name, Long manufacturerId, Long warehouseId, LocalDate dateOfManufacturing,
                   LocalDate dateOfPlacementToWarehouse, BigDecimal price, BigDecimal discount) {
        setName(name);
        setManufacturerId(manufacturerId);
        setWarehouseId(warehouseId);
        setDateOfManufacturing(dateOfManufacturing);
        setDateOfPlacementToWarehouse(dateOfPlacementToWarehouse);
        setPrice(price);
        setDiscount(discount);
    }

    public Product(Long id, String name, Long manufacturerId, Long warehouseId, LocalDate dateOfManufacturing,
                   LocalDate dateOfPlacementToWarehouse, BigDecimal price, BigDecimal discount) {
        this(name, manufacturerId, warehouseId, dateOfManufacturing, dateOfPlacementToWarehouse, price, discount);
        setId(id);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException(
                    "Name can't be empty!"
            );

        this.name = name;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Long manufacturerId) {

        if (manufacturerId == null)
            throw new IllegalArgumentException(
                    "Id of manufacturer can't be empty!"
            );

        this.manufacturerId = manufacturerId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {

        if (warehouseId == null)
            throw new IllegalArgumentException(
                    "Id of warehouse can't be empty!"
            );

        this.warehouseId = warehouseId;
    }

    public LocalDate getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public void setDateOfManufacturing(LocalDate dateOfManufacturing) {
        this.dateOfManufacturing = dateOfManufacturing;
    }

    public LocalDate getDateOfPlacementToWarehouse() {
        return dateOfPlacementToWarehouse;
    }

    public void setDateOfPlacementToWarehouse(LocalDate dateOfPlacementToWarehouse) {
        this.dateOfPlacementToWarehouse = dateOfPlacementToWarehouse;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {

        if (price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException(
                    "Price can't be less than zero or equal to zero"
            );

        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {

        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.valueOf(MAX_DISCOUNT)) > 0)
            throw new IllegalArgumentException(
                    "Discount must be between 0 and %.2f".formatted(
                            MAX_DISCOUNT
                    )
            );

        this.discount = discount;
    }

}
