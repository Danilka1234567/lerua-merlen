package model.service;

import infrastructure.exceptions.repository.ForeignKeyException;
import infrastructure.exceptions.service.UnknownEntityException;
import model.dto.request.ProductRequestDto;
import model.dto.response.ProductResponseDto;
import model.entities.Product;
import model.repository.implementations.manufacturer.ManufacturerRepositoryImpl;
import model.repository.implementations.product.ProductRepositoryImpl;
import model.repository.implementations.warehouse.WarehouseRepositoryImpl;
import model.repository.interfaces.ManufacturerRepository;
import model.repository.interfaces.ProductRepository;
import model.repository.interfaces.WarehouseRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final WarehouseRepository warehouseRepository;

    public ProductService(ProductRepository productRepository, ManufacturerRepository manufacturerRepository,
                        WarehouseRepository warehouseRepository){
        this.productRepository = productRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    public ProductResponseDto create(ProductRequestDto request){
        if (! manufacturerRepository.existsById(request.getManufacturerId()))
            throw new UnknownEntityException(
                    "Can't register new product: can't find manufacturer with id " + request.getManufacturerId()
            );

        if (! warehouseRepository.existsById(request.getWarehouseId()))
            throw new UnknownEntityException(
                    "Can't register new product: can't find warehouse with id " + request.getWarehouseId()
            );

        Product entity = mapRequestToEntity(request);
        Long id = productRepository.save(entity);
        entity.setId(id);
        return mapEntityToResponse(entity);
    }

    public void saveBatch(List<ProductRequestDto> request){
        List<Product> products = request.stream().map(ProductService::mapRequestToEntity).toList();
        try{
            productRepository.saveBatch(products);
        }catch (ForeignKeyException e){
            throw new UnknownEntityException(
                    "Invalid data in batch: foreign key violation"
            );
        }
    }

    public void remove(Long id){
        int affectedRows = productRepository.remove(id);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't remove product: unknown product id"
            );
    }

    public void update(ProductRequestDto request, Long id){
        Product entity = mapRequestToEntity(request);
        entity.setId(id);
        int affectedRows = productRepository.update(entity);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't update product: unknown product id " + id
            );
    }

    public ProductResponseDto findById(Long id){
        Product entity = productRepository.findById(id).orElseThrow(
                () -> new UnknownEntityException("Can't find product by id" + id + ": no such id in db")
        );
        return mapEntityToResponse(entity);
    }

    public List<ProductResponseDto> findAllByWarehouseId(Long id){
        List<Product> products = productRepository.findAllByWarehouseId(id);
        return products.stream().map(ProductService::mapEntityToResponse).toList();
    }

    public List<ProductResponseDto> findAllByManufacturerId(Long id){
        List<Product> products = productRepository.findAllByManufacturerId(id);
        return products.stream().map(ProductService::mapEntityToResponse).toList();
    }

    private static ProductResponseDto mapEntityToResponse(Product entity){
        return new ProductResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getManufacturerId(),
                entity.getWarehouseId(),
                entity.getDateOfManufacturing(),
                entity.getDateOfPlacementToWarehouse(),
                entity.getPrice(),
                entity.getDiscount()
        );
    }

    private static Product mapRequestToEntity(ProductRequestDto request){
        return new Product(
                request.getName(),
                request.getManufacturerId(),
                request.getWarehouseId(),
                request.getDateOfManufacturing(),
                request.getDateOfPlacementToWarehouse(),
                request.getPrice(),
                request.getDiscount()
        );
    }
}
