package model.repository;

import model.dto.ProductDetails;
import model.entity.Product;
import model.exception.ForeignKeyViolationException;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.exception.UniqueViolationException;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository{

    Id save(Product entity, Connection conn) throws GeneratedKeysException, RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    int update(Product entity, Id id, Connection conn) throws RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    List<Product> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<Product> findById(Id id, Connection conn) throws RepositoryException;
    List<Product> findAllByWarehouseId(Id warehouseId, Connection conn) throws RepositoryException;
    List<Product> findAllByManufacturerId(Id manufacturerId, Connection conn) throws RepositoryException;
    List<Product> findAllLikeName(String name, Connection conn) throws RepositoryException;
    List<ProductDetails> findProductsDetails(String manufacturerName, Connection conn) throws RepositoryException;
}
