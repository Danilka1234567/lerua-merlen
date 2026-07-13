package model.dto.response;

import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public record ManufacturerResponseDto(
        Long id,
        Email email,
        PhoneNumber number,
        String name,
        Address address
) {
}
