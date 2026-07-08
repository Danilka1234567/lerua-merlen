package domain.entities.base_entities;

import domain.entities.abstract_entities.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductEntity extends BaseEntity {

    private final static double MAX_DISCOUNT = 0.75;

    private Long manufacturerId;
    private Long warehouseId;
    private LocalDate dateOfManufacturing;
    private LocalDate dateOfPlacementToWarehouse;
    private BigDecimal price;
    private double discount;

    public ProductEntity(Long manufacturerId, Long warehouseId, LocalDate dateOfManufacturing,
                         LocalDate dateOfPlacementToWarehouse, BigDecimal price, double discount) {

    }

    public ProductEntity(Long id, Long manufacturerId, Long warehouseId, LocalDate dateOfManufacturing,
                         LocalDate dateOfPlacementToWarehouse, BigDecimal price, double discount) {
        setId(id);
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {

        if (discount < 0 || discount > MAX_DISCOUNT)
            throw new IllegalArgumentException(
                    "Discount must be between 0 and %.2f".formatted(
                            MAX_DISCOUNT
                    )
            );

        this.discount = discount;
    }


    public BigDecimal calculateFinalCost(){
        BigDecimal discountSum = price.multiply(BigDecimal.valueOf(discount));
        return price.subtract(discountSum);
    }

}
