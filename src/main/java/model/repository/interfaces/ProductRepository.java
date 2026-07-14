package model.repository.interfaces;

import model.entities.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product> {

    List<Product> findAllByManufacturerId(Long id);
    List<Product> findAllByWarehouseId(Long id);


}
