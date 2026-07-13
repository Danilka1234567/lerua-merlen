package model.dto.response;

import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public record UserResponseDto(
        Long id,
        Email email,
        PhoneNumber phoneNumber,
        String name,
        String surname
) {
}
