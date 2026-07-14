package model.repository.interfaces;

import model.entities.Warehouse;
import model.valueobjects.Address;

import java.util.Optional;

public interface WarehouseRepository extends BaseRepository<Warehouse> {

    Optional<Warehouse> findByAddress(Address address);
    boolean existsByAddress(Address address);

}
