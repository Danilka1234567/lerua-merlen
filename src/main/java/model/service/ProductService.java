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

import javax.sql.rowset.serial.SerialException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        Validator.validateNotNull(productRepository, "Product repository");

        this.productRepository = productRepository;
    }


    public Id create(Product product){
        Validator.validateNotNull(product, "Product");
        Connection conn = ConnectionManager.getConnectionSingletone();

        return Transaction.complete(conn, () -> {
            return productRepository.save(product, conn);
        });
    }


    public void updateInfo(Id warehouseId, Id manufacturerId, String name,
                           BigDecimal price, BigDecimal discount, Id productId){
        Validator.validateNotNull(warehouseId, "Warehouse id");
        Validator.validateNotNull(manufacturerId, "Manufacturer id");
        Validator.validateNotNull(name, "Name");
        Validator.validateNotNull(price, "Price");
        Validator.validateNotNull(discount, "Discount");
        Validator.validateNotNull(productId, "Product id");


        Connection conn = ConnectionManager.getConnectionSingletone();

        Product productFromDb = productRepository.findById(productId, conn).orElseThrow(
                () -> new EntityNotFoundException("Unknown product id")
        );

        Product productToUpdate = Product.loadFromDb(
                productId,
                productFromDb.isDeleted(),
                warehouseId,
                manufacturerId,
                name,
                price,
                discount,
                null,
                null
        );
        int affectedRows = Transaction.complete(conn, () ->
            productRepository.update(productToUpdate, productId,
                    conn)
        );

        if (affectedRows == 0)
            throw new ServiceException(
                    "Failed to update product info"
            );
    }

    public void markAsDeleted(Id productId){
        Validator.validateNotNull(productId, "Product id");
        int affectedRows = productRepository.setDeletionStatus(
                true, productId, ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't mark product as deleted"
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

    public List<Product> getAllLikeName(String name){
        return productRepository.findAllLikeName(name, ConnectionManager.getConnectionSingletone());
    }
}
