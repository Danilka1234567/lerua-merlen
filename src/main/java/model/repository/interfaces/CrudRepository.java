package model.repository.interfaces;

import java.util.Optional;

public interface CrudRepository<T> {

    long save(T obj);
    Optional<T> findById(Long id);
    int update(T obj);
    void remove(Long id);

}
