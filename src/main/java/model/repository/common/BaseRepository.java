package model.repository.common;

import model.entity.abstr.BaseEntity;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    Id save(T entity, Connection conn) throws GeneratedKeysException, RepositoryException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
}
