package model.dto.request;

import model.enums.UserRole;
import model.service.shared.Validator;
import model.vo.ContactInfo;
import model.vo.Password;

public record UserRequestDto(ContactInfo contactInfo, UserRole role, Password password, String name){
    public UserRequestDto {
        Validator.validateNotNull(contactInfo, "Contact info");
        Validator.validateNotNull(role, "User role");
        Validator.validateNotNull(password, "Password");
        Validator.validateNotNull(name, "Name");
        if (name.length() < 3 || name.length() > 64){
            throw new IllegalArgumentException(
                    "Name's length must be bigger than 2 and smaller than 65"
            );
        }
    }
}
