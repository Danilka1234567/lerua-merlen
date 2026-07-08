package domain.repository_interfaces;

import domain.entities.physically_contactable_entities.ManufacturerEntity;
import domain.value_objects.Address;

import java.util.List;

public interface ManufacturerRepository extends CrudRepository<ManufacturerEntity>{

    List<ManufacturerEntity> findAllBySpecialization(String specialization);
    List<ManufacturerEntity> findAllByAddress(Address address);
    boolean existsByAddress(Address address);

}
