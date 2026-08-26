package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Session;
import model.exception.RepositoryException;
import model.exception.ServiceException;
import model.repository.SessionRepository;
import model.repository.UserRepository;
import model.exception.EntityNotFoundException;
import model.service.shared.Transaction;
import model.vo.Id;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Optional;

public class SessionService {

    private final SessionRepository sessionRepository;
    private final static int LIVING_PERIOD_DAYS = 1;

    public SessionService(SessionRepository sessionRepository){
        if (sessionRepository == null)
            throw new IllegalArgumentException(
                    "Session repository can't be null"
            );

        this.sessionRepository = sessionRepository;
    }

    public Id create(Id userId){
        Connection conn = ConnectionManager.getConnectionSingletone();
        return Transaction.complete(conn, () -> {
            Session session = Session.createNew(userId, LocalDateTime.now().plusDays(LIVING_PERIOD_DAYS));
            return sessionRepository.save(session, conn);
        });
    }


    public boolean isLogged(Id userId){
        Connection conn = ConnectionManager.getConnectionSingletone();

        Optional<Session> session = sessionRepository.findByUserId(userId, conn);
        if (session.isEmpty())
            return false;

        if (session.get().getExpirationDate().isBefore(LocalDateTime.now())){
            sessionRepository.setDeletionStatus(true, session.get().getId(), conn);
            return false;
        }

        return true;
    }

    public int cleanDeleted(){
        Connection conn = ConnectionManager.getConnectionSingletone();
        return sessionRepository.remove(conn);
    }
}
