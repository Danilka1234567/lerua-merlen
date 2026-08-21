package model.entity;

import model.entity.abstr.ExtendedEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product extends ExtendedEntity {

    private Long warehouseId;
    private Long manufacturerId;
    private String name;
    private BigDecimal price;
    private BigDecimal discount;

    private Warehouse warehouse;
    private Manufacturer manufacturer;


    public static Product createNew(Long warehouseId, Long manufacturerId, String name,
                                    BigDecimal price, BigDecimal discount){
        return new Product(warehouseId, manufacturerId, name, price, discount);
    }

    public static Product loadFromDb(Long id, boolean isDeleted, LocalDate registrationDate, Long warehouseId,
                                     Long manufacturerId, String name, BigDecimal price, BigDecimal discount,
                                     Warehouse warehouse, Manufacturer manufacturer){
        return new Product(id, isDeleted, registrationDate, warehouseId, manufacturerId, name, price, discount,
                            warehouse, manufacturer);
    }

    private Product(Long warehouseId, Long manufacturerId, String name, BigDecimal price, BigDecimal discount) {
        setWarehouseId(warehouseId);
        setManufacturerId(manufacturerId);
        setName(name);
        setPrice(price);
        setDiscount(discount);
    }

    private Product(Long id, boolean isDeleted, LocalDate registrationDate, Long warehouseId,
                   Long manufacturerId, String name, BigDecimal price, BigDecimal discount, Warehouse warehouse,
                   Manufacturer manufacturer) {
        super(id, isDeleted, registrationDate);
        setWarehouseId(warehouseId);
        setManufacturerId(manufacturerId);
        setName(name);
        setPrice(price);
        setDiscount(discount);
        setWarehouse(warehouse);
        setManufacturer(manufacturer);
    }

    private void setWarehouseId(Long warehouseId) {
        if (warehouseId == null)
            throw new IllegalArgumentException(
                    "warehouseId can't be null"
            );

        this.warehouseId = warehouseId;
    }

    private void setManufacturerId(Long manufacturerId) {
        if (manufacturerId == null)
            throw new IllegalArgumentException(
                    "manufacturer id is null"
            );

        this.manufacturerId = manufacturerId;
    }

    private void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException(
                    "name can't be null"
            );

        if (name.length() > 255)
            throw new IllegalArgumentException(
                    "name can't be more than 255 symbols length"
            );

        this.name = name;
    }

    private void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException(
                    "price can't be less than zero or equal to it"
            );

        this.price = price;
    }


    private void setDiscount(BigDecimal discount) {
        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.ONE) >= 0)
            throw new IllegalArgumentException(
                    "discount can't be less than zero or >= 1"
            );

        this.discount = discount;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public Long getManufacturerId() {
        return manufacturerId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    private void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    private void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
}
