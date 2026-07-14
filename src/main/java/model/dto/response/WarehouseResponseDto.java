package model.dto.response;

import infrastructure.exceptions.dto.NotNullException;
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
    public WarehouseResponseDto{
        if (id == null)
            throw new NotNullException("id");
        if (email == null)
            throw new NotNullException("email");
        if (phoneNumber == null)
            throw new NotNullException("phoneNumber");
        if (name == null)
            throw new NotNullException("name");
        if (address == null)
            throw new NotNullException("address");
    }
}
