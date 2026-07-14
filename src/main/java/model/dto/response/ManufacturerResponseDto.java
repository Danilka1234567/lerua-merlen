package model.dto.response;


import infrastructure.exceptions.dto.NotNullException;
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
    public ManufacturerResponseDto{

        String exceptionMessage = "Field %s can't be null";
        if (id == null)
            throw new NotNullException("id");
        if (email == null)
            throw new NotNullException("email");
        if (number == null)
            throw new NotNullException("phoneNumber");
        if (name == null)
            throw new NotNullException("name");
        if (address == null)
            throw new NotNullException("address");
    }
}
