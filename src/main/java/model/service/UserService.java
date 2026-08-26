package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.User;
import model.enums.UserRole;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.UserRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.*;

import java.sql.Connection;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;

    public UserService(UserRepository userRepository, SessionService sessionService){
        Validator.validateNotNull(userRepository, "User repository");
        Validator.validateNotNull(sessionService, "Session service");
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }


    public Id loginUser(Email email, Password password){
        Validator.validateNotNull(email, "Email");
        Validator.validateNotNull(password, "Password");
        Connection conn = ConnectionManager.getConnectionSingletone();

        User userFromDb = userRepository.findByEmail(email, conn).orElseThrow(
                () -> new EntityNotFoundException("Unknown user email")
        );

        /// Пароли - просто VO, не хешируются, не кодируются! допустимо только в рамках учебного проекта!
        if (! userFromDb.getPassword().equals(password))
            throw new ServiceException("Wrong password");

        Id userId = userFromDb.getId();
        sessionService.create(userId);
        return userId;
    }


    public Id registerUser(User user){
        Validator.validateNotNull(user, "User");
        Connection conn = ConnectionManager.getConnectionSingletone();

        ContactInfo contactInfo = user.getContactInfo();

        return Transaction.complete(conn, () -> {
            Id userId =  userRepository.save(user, conn);
            sessionService.create(userId);
            return userId;
        });
    }



    public void updateUserInfo(User user, Id userId){
        Validator.validateNotNull(user, "User");
        Validator.validateNotNull(userId, "User id");

        Connection conn = ConnectionManager.getConnectionSingletone();
        Transaction.complete(conn, () -> {
            int affectedRows = userRepository.update(user, userId, ConnectionManager.getConnectionSingletone());
            if (affectedRows == 0)
                throw new ServiceException("Can't update user's info");
            }
        );
    }


    public void markAsDeleted(Id userId){
        Validator.validateNotNull(userId, "Order id");
        int affectedRows = userRepository.setDeletionStatus(
                true, userId, ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't mark user as deleted"
            );
    }

    public List<User> getDeletedUsers(){
        return userRepository.findAllByDeleteStatus(true, ConnectionManager.getConnectionSingletone());
    }

    public int cleanDeleted(){
        return userRepository.remove(ConnectionManager.getConnectionSingletone());
    }

    public User getUserById(Id userId){
        Validator.validateNotNull(userId, "User id");
        return userRepository.findById(userId, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown user id")
        );
    }

    public User getUserByEmail(Email email){
        Validator.validateNotNull(email, "Email");
        return userRepository.findByEmail(email, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown user email")
        );
    }

    public User getUserByPhoneNumber(PhoneNumber phoneNumber){
        Validator.validateNotNull(phoneNumber, "Phone number");
        return userRepository.findByPhoneNumber(phoneNumber, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown user phone number")
        );
    }

    public List<User> getAllByRole(UserRole role){
        Validator.validateNotNull(role, "User role");
        return userRepository.findAllByRole(role, ConnectionManager.getConnectionSingletone());
    }
}
