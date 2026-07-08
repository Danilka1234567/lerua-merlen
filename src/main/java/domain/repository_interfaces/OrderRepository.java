package domain.repository_interfaces;

import domain.entities.base_entities.OrderEntity;
import domain.value_objects.Address;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity>{

    List<OrderEntity> findAllByAddress(Address address);
    List<OrderEntity> findAllByUserId(Long id);
    List<OrderEntity> findAllByProductId(Long id);
    List<OrderEntity> findAllByArrivingDate(LocalDate date);
    List<OrderEntity> findAllByCreationDate(LocalDate date);


}
