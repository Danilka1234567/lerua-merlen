package model.repository;

import model.entity.Session;
import model.repository.common.BaseRepository;

import java.sql.Connection;
import java.util.Optional;

public interface SessionRepository extends BaseRepository<Session> {

    Optional<Session> findByUserId(Long userId, Connection conn);

}
