package model.repository;

import model.entity.Product;
import model.repository.common.ExtendedRepository;
import model.repository.common.ReferencableRepository;

import java.sql.Connection;
import java.util.List;

public interface ProductRepository extends ExtendedRepository<Product>, ReferencableRepository {

    List<Product> findAllByWarehouseId(Long warehouseId, Connection conn);
    List<Product> findAllByManufacturerId(Long manufacturerId, Connection conn);
    List<Product> findAllByManufacturerIdAndWarehouseId(Long id, Connection conn);

}
