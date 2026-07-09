package model.repository.interfaces;

import model.entities.Order;
import model.valueobjects.Address;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order>{

    List<Order> findAllByAddress(Address address);
    List<Order> findAllByUserId(Long id);
    List<Order> findAllByProductId(Long id);
    List<Order> findAllByArrivingDate(LocalDate date);
    List<Order> findAllByCreationDate(LocalDate date);


}
