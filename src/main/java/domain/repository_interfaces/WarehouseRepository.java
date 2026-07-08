package domain.repository_interfaces;

import domain.entities.physically_contactable_entities.WarehouseEntity;
import domain.value_objects.Address;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<WarehouseEntity> {

    Optional<WarehouseEntity> findByAddress(Address address);
    boolean existsByAddress(Address address);

}
