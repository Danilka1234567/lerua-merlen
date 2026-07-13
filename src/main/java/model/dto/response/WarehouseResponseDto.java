package model.dto.response;

import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public record WarehouseResponseDto(
        Long id,
        Email email,
        PhoneNumber phoneNumber,
        String name,
        Address address,
        int capacity
) {
}
