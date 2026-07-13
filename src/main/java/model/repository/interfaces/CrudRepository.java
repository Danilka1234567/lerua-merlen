package model.repository.interfaces;

import java.util.Optional;

public interface CrudRepository<T> {

    long save(T entity);
    Optional<T> findById(Long id);
    int update(T entity);
    int remove(Long id);

}
