package model.repository.common;

import model.entity.abstr.BaseEntity;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public interface BaseRepository<T extends BaseEntity> {
    Id save(T entity, Connection conn);
    int setDeletionStatus(boolean status, Id id, Connection conn);
    int remove(Connection conn);
}
