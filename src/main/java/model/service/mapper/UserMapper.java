package model.service.mapper;

import model.dto.request.UserRequestDto;
import model.entity.User;

public class UserMapper {

    public static User mapRequestToEntity(UserRequestDto request){
        return User.createNew(request.contactInfo(), request.name(), request.password(), request.role());
    }

}
