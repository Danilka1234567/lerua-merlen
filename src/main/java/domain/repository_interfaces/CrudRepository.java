package domain.repository_interfaces;

import java.util.Optional;

public interface CrudRepository<T> {

    void save(T obj);
    Optional<T> findById(Long id);
    void update(T obj);
    void remove(T obj);

}
