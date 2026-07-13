package model.dto.response;

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
}
