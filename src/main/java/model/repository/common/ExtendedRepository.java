package model.repository.common;

import model.entity.abstr.ExtendedEntity;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExtendedRepository<T extends ExtendedEntity> extends BaseRepository<T> {

    List<T> findAllByRegDate(LocalDate date, Connection conn);
    List<T> findAllBetweenRegDate(LocalDate start, LocalDate end, Connection conn);
    int update(T entity, Long id, Connection conn);
    List<T> findAllByDeleteStatus(boolean status, Connection conn);
    Optional<T> findById(Long id, Connection conn);

}
