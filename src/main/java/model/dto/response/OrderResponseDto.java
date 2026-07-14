package model.dto.response;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.dto.NotNullException;
import model.valueobjects.Address;
import utils.enums.OrderStatus;

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
    public OrderResponseDto{
        if (id == null)
            throw new NotNullException("id");
        if (userId == null)
            throw new NotNullException("userId");
        if (productId == null)
            throw new NotNullException("productId");
        if (deliveryAddress == null)
            throw new NotNullException("deliveryAddress");
        if (dateOfCreation == null)
            throw new NotNullException("dateOfCreation");
        if (status == null)
            throw new NotNullException("status");
    }
}
