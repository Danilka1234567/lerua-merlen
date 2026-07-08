package domain.repository_interfaces;

import domain.entities.base_entities.ProductEntity;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity> {

    List<ProductEntity> findAllByManufacturerId(Long id);
    List<ProductEntity> findAllByWarehouseId(Long id);


}
