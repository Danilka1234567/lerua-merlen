package model.dto.request;

import model.vo.FullAddress;
import model.vo.Id;

public record OrderRequestDto(Id userId, Id productId, int deliveryPeriod, FullAddress deliveryAddress) {

    public OrderRequestDto {

        if (userId == null)
            throw new IllegalArgumentException(
                    "User id can't be null"
            );

        if (productId == null)
            throw new IllegalArgumentException(
                    "Product id can't be null"
            );
        if (deliveryPeriod <= 0)
            throw new IllegalArgumentException(
                    "Delivery period must be bigger than 0"
            );
        if (deliveryAddress == null)
            throw new IllegalArgumentException(
                    "Delivery address can't be null"
            );
    }
}
