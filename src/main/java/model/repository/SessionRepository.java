package model.repository;

import model.entity.Session;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.vo.Id;

import java.sql.Connection;
import java.util.Optional;

public interface SessionRepository{

    Id save(Session entity, Connection conn) throws GeneratedKeysException, RepositoryException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    Optional<Session> findByUserId(Id userId, Connection conn) throws RepositoryException;

}
