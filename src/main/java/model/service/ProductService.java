package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Product;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.ManufacturerRepository;
import model.repository.ProductRepository;
import model.repository.WarehouseRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.Id;

import java.sql.Connection;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ManufacturerRepository manufacturerRepository;

    public ProductService(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                          ManufacturerRepository manufacturerRepository) {

        Validator.validateNotNull(productRepository, "Product repository");
        Validator.validateNotNull(warehouseRepository, "Warehouse repository");
        Validator.validateNotNull(manufacturerRepository, "Manufacturer repository");

        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    public Id create(Product product){
        Validator.validateNotNull(product, "Product");
        Connection conn = ConnectionManager.getConnectionSingletone();

        return Transaction.complete(conn, () -> {

            if (! warehouseRepository.existsById(product.getWarehouseId(), conn)){
                throw new EntityNotFoundException(
                        "Unknown warehouse id"
                );
            }

            if (! manufacturerRepository.existsById(product.getManufacturerId(), conn)){
                throw new EntityNotFoundException(
                        "Unknown manufacturer repository"
                );
            }

            return productRepository.save(product, conn);
        });
    }


    public void updateInfo(Product product, Id productId){
        Validator.validateNotNull(product, "Product");
        Validator.validateNotNull(productId, "Product id");

        int affectedRows = productRepository.update(product, productId,
                ConnectionManager.getConnectionSingletone());
    }

    public void markAsDeleted(Id productId){
        Validator.validateNotNull(productId, "Product id");
        int affectedRows = productRepository.setDeletionStatus(
                true, productId, ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't mark prouct as deleted"
            );
    }

    public int cleanDeleted(){
        return productRepository.remove(ConnectionManager.getConnectionSingletone());
    }

    public List<Product> getDeleted(){
        return productRepository.findAllByDeleteStatus(true, ConnectionManager.getConnectionSingletone());
    }

    public Product getById(Id productId){
        return productRepository.findById(productId, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown product id")
        );
    }

    public List<Product> getAllByWarehouseId(Id warehouseId){
        return productRepository.findAllByWarehouseId(warehouseId, ConnectionManager.getConnectionSingletone());
    }

    public List<Product> getAllByManufacturerId(Id manufacturerId){
        return productRepository.findAllByManufacturerId(manufacturerId, ConnectionManager.getConnectionSingletone());
    }
}
