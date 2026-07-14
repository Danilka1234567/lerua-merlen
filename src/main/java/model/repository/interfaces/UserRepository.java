package model.repository.interfaces;

import model.entities.User;
import model.valueobjects.Email;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User>{

    Optional<User> findByEmail(Email email);
    boolean existsByEmail(Email email);

}
