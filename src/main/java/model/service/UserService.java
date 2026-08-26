package model.service;

import infrastructure.config.ConnectionManager;
import model.dto.request.UserLoginDto;
import model.dto.request.UserRequestDto;
import model.entity.User;
import model.enums.UserRole;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.UserRepository;
import model.service.mapper.UserMapper;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.ContactInfo;
import model.vo.Email;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.time.LocalDate;
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


    public Id loginUser(UserLoginDto request){
        Validator.validateNotNull(request, "Request dto");
        Connection conn = ConnectionManager.getConnectionSingletone();

        User userFromDb = userRepository.findByEmail(request.email(), conn).orElseThrow(
                () -> new EntityNotFoundException("Unknown user email")
        );

        if (! userFromDb.getName().equals(request.name()))
            throw new ServiceException("Wrong user name");

        /// Пароли - просто VO, не хешируются, не кодируются! допустимо только в рамках учебного проекта!
        if (! userFromDb.getPassword().equals(request.password()))
            throw new ServiceException("Wrong password");

        Id userId = userFromDb.getId();
        sessionService.create(userId);
        return userId;
    }


    public Id registerUser(UserRequestDto request){
        Validator.validateNotNull(request, "Request dto");
        Connection conn = ConnectionManager.getConnectionSingletone();

        ContactInfo contactInfo = request.contactInfo();

        return Transaction.complete(conn, () -> {
            if (userRepository.existsByEmail(contactInfo.getEmail(), conn))
                throw new ServiceException(
                        "User with such email is already exists in database"
                );

            if (contactInfo.getPhoneNumber() != null &&
                    userRepository.existsByPhoneNumber(contactInfo.getPhoneNumber(), conn))
                throw new ServiceException(
                        "User with such phone number is already exists in database"
                );

            Id userId =  userRepository.save(UserMapper.mapRequestToEntity(request), conn);
            sessionService.create(userId);
            return userId;
        });
    }



    public void updateUserInfo(UserRequestDto request, Id userId){
        Validator.validateNotNull(request, "Request dto");
        Validator.validateNotNull(userId, "User id");

        User user = UserMapper.mapRequestToEntity(request);
        int affectedRows = userRepository.update(user, userId, ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException("Can't update user's info");
    }


    public void markAsDeleted(Id userId){
        Validator.validateNotNull(userId, "Order id");
        userRepository.setDeletionStatus(true, userId, ConnectionManager.getConnectionSingletone());
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

    public List<User> getAllByRegistrationDate(LocalDate regDate){
        Validator.validateNotNull(regDate, "Registration date");
        return userRepository.findAllByRegDate(regDate, ConnectionManager.getConnectionSingletone());
    }

    public List<User> getAllBetweenRegDates(LocalDate start, LocalDate end){
        Validator.validateNotNull(start, "Starting date");
        Validator.validateNotNull(end, "Ending date");
        return userRepository.findAllBetweenRegDate(start, end, ConnectionManager.getConnectionSingletone());
    }

    public List<User> getAllByRole(UserRole role){
        Validator.validateNotNull(role, "User role");
        return userRepository.findAllByRole(role, ConnectionManager.getConnectionSingletone());
    }
}
