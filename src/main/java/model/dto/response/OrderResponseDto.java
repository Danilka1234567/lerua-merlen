package model.dto.response;

import model.valueobjects.Address;
import utils.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderResponseDto(
        Long id,
        Long userId,
        Long productId,
        Address deliveryAddress,
        LocalDate dateOfCreation,
        int deliveryPeriod,
        OrderStatus status
) {
}
