package model.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductRequestDto {

    private String name;
    private Long manufacturerId;
    private Long warehouseId;
    private LocalDate dateOfManufacturing;
    private LocalDate dateOfPlacementToWarehouse;
    private BigDecimal price;
    private BigDecimal discount;

    public ProductRequestDto(String name, Long manufacturerId, Long warehouseId, LocalDate dateOfManufacturing,
                             LocalDate dateOfPlacementToWarehouse, BigDecimal price, BigDecimal discount) {
        setName(name);
        setManufacturerId(manufacturerId);
        setWarehouseId(warehouseId);
        setDateOfManufacturing(dateOfManufacturing);
        setDateOfPlacementToWarehouse(dateOfPlacementToWarehouse);
        setPrice(price);
        setDiscount(discount);
    }

    public void setName(String name) {

        if (name == null || name.isBlank())
            throw new IllegalArgumentException(
                    "Can't assign name to product dto: name is null or blank"
            );
        this.name = name;
    }

    public void setManufacturerId(Long manufacturerId) {
        if (manufacturerId == null || manufacturerId < 0)
            throw new IllegalArgumentException(
                    "Can't assign manufacturer id to product dto: id is null or id < 0"
            );
        this.manufacturerId = manufacturerId;
    }

    public void setWarehouseId(Long warehouseId) {
        if (warehouseId == null || warehouseId < 0)
            throw new IllegalArgumentException(
                    "Can't assign warehouse id to product dto: id is null or id < 0"
            );
        this.warehouseId = warehouseId;
    }

    public void setDateOfManufacturing(LocalDate dateOfManufacturing) {
        if (dateOfManufacturing == null)
            throw new IllegalArgumentException(
                    "Can't assign date of manufacturing to product dto: date is null"
            );
        this.dateOfManufacturing = dateOfManufacturing;
    }

    public void setDateOfPlacementToWarehouse(LocalDate dateOfPlacementToWarehouse) {
        if (dateOfPlacementToWarehouse == null)
            throw new IllegalArgumentException(
                    "Can't assign date of placement to warehouse to product dto: date is null"
            );
        this.dateOfPlacementToWarehouse = dateOfPlacementToWarehouse;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException(
                    "Can't assign price to product dto: price is <= 0"
            );

        this.price = price;
    }

    public void setDiscount(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) <= 0 || discount.compareTo(BigDecimal.ONE) >= 1)
            throw new IllegalArgumentException(
                    "Can't assign discount to product dto: discount must be > 0 and < 1"
            );
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public LocalDate getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public LocalDate getDateOfPlacementToWarehouse() {
        return dateOfPlacementToWarehouse;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
