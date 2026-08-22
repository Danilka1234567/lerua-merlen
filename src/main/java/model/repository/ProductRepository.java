package model.repository;

import model.entity.Product;
import model.repository.common.ExtendedRepository;
import model.repository.common.ReferencableRepository;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public interface ProductRepository extends ExtendedRepository<Product>, ReferencableRepository {

    List<Product> findAllByWarehouseId(Id warehouseId, Connection conn);
    List<Product> findAllByManufacturerId(Id manufacturerId, Connection conn);
    List<Product> findAllByManufacturerIdAndWarehouseId(Id manufacturerId, Id warehouseId, Connection conn);

}
