package model.dto.response;

import infrastructure.exceptions.dto.NotNullException;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductResponseDto(
        Long id,
        String name,
        Long manufacturerId,
        Long warehouseId,
        LocalDate dateOfManufacturing,
        LocalDate dateOfPlacementToWarehouse,
        BigDecimal price,
        BigDecimal discount
) {
    public ProductResponseDto{
        if (id == null)
            throw new NotNullException("id");
        if (name == null)
            throw new NotNullException("name");
        if (manufacturerId == null)
            throw new NotNullException("manufacturerId");
        if (warehouseId == null)
            throw new NotNullException("warehouseId");
        if (dateOfManufacturing == null)
            throw new NotNullException("dateOfManufacturing");
        if (dateOfPlacementToWarehouse == null)
            throw new NotNullException("dateOfPlacementToWarehouse");
        if (price == null)
            throw new NotNullException("price");
        if (discount == null)
            throw new NotNullException("discount");
    }
}
