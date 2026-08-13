package model.repository.common;

import model.entity.abstr.ExtendedEntity;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExtendedRepository<T extends ExtendedEntity> extends BaseRepository<T> {

    List<T> findAllByRegDate(Date date, Connection conn);
    List<T> findAllBetweenRegDate(Date start, Date end, Connection conn);
    int update(T entity, Connection conn);
    List<T> findAllByDeleteStatus(boolean status, Connection conn);
    Optional<T> findById(Long id, Connection conn);

}
