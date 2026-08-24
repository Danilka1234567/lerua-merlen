package model.repository;

import model.entity.Session;
import model.exception.RepositoryException;
import model.repository.common.BaseRepository;
import model.vo.Id;

import java.sql.Connection;
import java.util.Optional;

public interface SessionRepository extends BaseRepository<Session> {

    Optional<Session> findByUserId(Id userId, Connection conn) throws RepositoryException;

}
