package model.repository.interfaces;

import model.entities.Manufacturer;
import model.valueobjects.Address;
import model.valueobjects.Email;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends CrudRepository<Manufacturer>{

    List<Manufacturer> findAllBySpecialization(String specialization);
    Optional<Manufacturer> findByAddress(Address address);
    boolean existsByAddress(Address address);
    Optional<Manufacturer> findByEmail(Email email);
    boolean existsByEmail(Email email);

}
