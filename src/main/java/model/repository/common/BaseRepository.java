package model.repository.common;

import model.entity.abstr.BaseEntity;

import java.sql.Connection;
import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    Long save(T entity, Connection conn);
    int setDeletionStatus(boolean status, Long id, Connection conn);
    int remove(Connection conn);
}
