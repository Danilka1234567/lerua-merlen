package model.repository.interfaces;

import model.entities.base.BaseEntity;

public interface BaseRepository<T extends BaseEntity> extends CrudRepository<T> {

    boolean existsById(Long id);

}
