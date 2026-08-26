package model.repository;

import model.entity.Product;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.vo.Id;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository{

    Id save(Product entity, Connection conn) throws GeneratedKeysException, RepositoryException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    List<Product> findAllByRegDate(LocalDate date, Connection conn) throws RepositoryException;
    List<Product> findAllBetweenRegDate(LocalDate start, LocalDate end, Connection conn) throws RepositoryException;
    int update(Product entity, Id id, Connection conn) throws RepositoryException;
    List<Product> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<Product> findById(Id id, Connection conn) throws RepositoryException;
    List<Product> findAllByWarehouseId(Id warehouseId, Connection conn) throws RepositoryException;
    List<Product> findAllByManufacturerId(Id manufacturerId, Connection conn) throws RepositoryException;
    List<Product> findAllByManufacturerIdAndWarehouseId(Id manufacturerId, Id warehouseId, Connection conn) throws RepositoryException;
    boolean existsById(Id id, Connection conn) throws RepositoryException;
}
