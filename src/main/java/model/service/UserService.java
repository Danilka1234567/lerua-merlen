package model.service;

import infrastructure.exceptions.service.EntityAlreadyExistsException;
import infrastructure.exceptions.service.UnknownEntityException;
import model.dto.request.UserRequestDto;
import model.dto.response.UserResponseDto;
import model.entities.User;
import model.repository.implementations.user.UserRepositoryImpl;
import model.repository.interfaces.UserRepository;
import model.valueobjects.Email;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDto register(UserRequestDto request){

        if (userRepository.existsByEmail(request.getEmail()))
            throw new EntityAlreadyExistsException(
                    "Can't register new user: user with such email is already in database"
            );

        User userEntity = mapRequestToEntity(request);
        Long id = userRepository.save(userEntity);

        userEntity.setId(id);
        return mapEntityToResponse(userEntity);
    }

    public UserResponseDto findById(Long id){
        User entity = userRepository.findById(id).orElseThrow(
                () -> new UnknownEntityException(
                        "Can't find user with id " + id + " in datadase"
                )
        );

        return mapEntityToResponse(entity);
    }

    public void update(UserRequestDto request, Long id){

        User entity = mapRequestToEntity(request);
        entity.setId(id);

        int affectedRows = userRepository.update(entity);

        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't update user: can't find user with id " + id
            );
    }

    public void remove(Long id){
        int affectedRows = userRepository.remove(id);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't delete user: can't find user with id " + id
            );
    }

    public UserResponseDto findByEmail(Email email){
        User entity = userRepository.findByEmail(email).orElseThrow(
                () -> new UnknownEntityException(
                    "Can't find user with email: " + email.getValue()
                )
        );
        return mapEntityToResponse(entity);
    }

    private static User mapRequestToEntity(UserRequestDto request){
        return new User(
                request.getEmail(),
                request.getPhoneNumber(),
                request.getName(),
                request.getSurname()
        );
    }

    private static UserResponseDto mapEntityToResponse(User entity){
        return new UserResponseDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getName(),
                entity.getSurname()
        );
    }
}
