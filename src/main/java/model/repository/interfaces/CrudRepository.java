package model.repository.interfaces;

import java.util.Optional;

public interface CrudRepository<T> {

    Long save(T obj);
    Optional<T> findById(Long id);
    int update(T obj);
    int remove(Long id);

}
