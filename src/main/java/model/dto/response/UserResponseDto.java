package model.dto.response;

import infrastructure.exceptions.dto.NotNullException;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

public record UserResponseDto(
        Long id,
        Email email,
        PhoneNumber phoneNumber,
        String name,
        String surname
) {
    public UserResponseDto{
        if (id == null)
            throw new NotNullException("id");
        if (email == null)
            throw new NotNullException("email");
        if (phoneNumber == null)
            throw new NotNullException("phoneNumber");
        if (name == null)
            throw new NotNullException("name");
        if (surname == null)
            throw new NotNullException("surname");
    }
}
