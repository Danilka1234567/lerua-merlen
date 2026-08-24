package model.repository.common;

import model.entity.abstr.ExtendedEntity;
import model.exception.RepositoryException;
import model.vo.Id;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExtendedRepository<T extends ExtendedEntity> extends BaseRepository<T> {

    List<T> findAllByRegDate(LocalDate date, Connection conn) throws RepositoryException;
    List<T> findAllBetweenRegDate(LocalDate start, LocalDate end, Connection conn) throws RepositoryException;
    int update(T entity, Id id, Connection conn) throws RepositoryException;
    List<T> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<T> findById(Id id, Connection conn) throws RepositoryException;

}
