package model.service;

import infrastructure.exceptions.service.EmailAlreadyExistsException;
import model.entities.User;
import model.repository.implementations.user.UserRepositoryImpl;
import model.repository.interfaces.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepositoryImpl();

    public long save(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException(
                    "Can't save user: " + user.getEmail().getValue() + "already exists in database"
            );
        }


    }
}
