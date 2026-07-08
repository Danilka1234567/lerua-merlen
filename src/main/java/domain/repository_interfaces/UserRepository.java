package domain.repository_interfaces;

import domain.entities.contactable_entities.UserEntity;
import domain.value_objects.Email;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity>{

    Optional<UserEntity> findByEmail(Email email);
    boolean existsByEmail(Email email);

}
