package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Session;
import model.exception.RepositoryException;
import model.exception.ServiceException;
import model.repository.SessionRepository;
import model.repository.UserRepository;
import model.exception.EntityNotFoundException;
import model.vo.Id;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Optional;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final static int LIVING_PERIOD_DAYS = 1;

    public SessionService(SessionRepository sessionRepository, UserRepository userRepository){
        if (sessionRepository == null)
            throw new IllegalArgumentException(
                    "Session repository can't be null"
            );

        if(userRepository == null)
            throw new IllegalArgumentException(
                    "User repository can't be null"
            );

        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }


    private void validateUserId(Id userId, Connection conn){
        if (userId == null)
            throw new IllegalArgumentException(
                    "User id can't be null!"
            );

        if ( ! userRepository.existsById(userId, conn))
            throw new EntityNotFoundException("Unknown user");
    }


    public Id create(Id userId){
        Connection conn = ConnectionManager.getConnectionSingletone();
        return Transaction.complete(conn, () -> {
            validateUserId(userId, conn);
            Session session = Session.createNew(userId, LocalDateTime.now().plusDays(LIVING_PERIOD_DAYS));
            return sessionRepository.save(session, conn);
        });
    }


    public boolean isLogged(Id userId){
        Connection conn = ConnectionManager.getConnectionSingletone();
        try{
            validateUserId(userId, conn);

            Optional<Session> session = sessionRepository.findByUserId(userId, conn);

            if (session.isEmpty())
                return false;

            if (session.get().getExpirationDate().isBefore(LocalDateTime.now())){
                sessionRepository.setDeletionStatus(true, session.get().getId(), conn);
                return false;
            }

            return true;
        }catch (RepositoryException e){
            throw new ServiceException("Can't check user logging status", e);
        }
    }

    public int cleanDeleted(){
        Connection conn = ConnectionManager.getConnectionSingletone();
        return sessionRepository.remove(conn);
    }
}
