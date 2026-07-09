package model.repository.interfaces;

import model.entities.Manufacturer;
import model.valueobjects.Address;

import java.util.List;

public interface ManufacturerRepository extends CrudRepository<Manufacturer>{

    List<Manufacturer> findAllBySpecialization(String specialization);
    List<Manufacturer> findAllByAddress(Address address);
    boolean existsByAddress(Address address);

}
