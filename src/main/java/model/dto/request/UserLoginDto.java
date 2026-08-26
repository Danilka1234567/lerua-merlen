package model.dto.request;

import model.service.shared.Validator;
import model.vo.Email;
import model.vo.Password;

public record UserLoginDto(Email email, String name, Password password) {

    public UserLoginDto {
        Validator.validateNotNull(email, "Email");
        Validator.validateNotNull(name, "Name");
        Validator.validateNotNull(password, "Password");
    }
}
