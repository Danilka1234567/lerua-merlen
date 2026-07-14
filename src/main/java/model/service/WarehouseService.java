package model.service;

import infrastructure.exceptions.service.EntityAlreadyExistsException;
import infrastructure.exceptions.service.UnknownEntityException;
import model.dto.request.WarehouseRequestDto;
import model.dto.response.WarehouseResponseDto;
import model.entities.Warehouse;
import model.repository.implementations.warehouse.WarehouseRepositoryImpl;
import model.repository.interfaces.WarehouseRepository;
import model.valueobjects.Address;

public class WarehouseService {

    private final WarehouseRepository repository = new WarehouseRepositoryImpl();
    private static final int MIN_CAPACITY = 50;

    public WarehouseResponseDto register(WarehouseRequestDto request){

        if (repository.existsByAddress(request.getAddress()))
            throw new EntityAlreadyExistsException(
                    "Can't register new warehouse: warehouse with address " + request.getAddress() + "is already in db"
            );

        if (request.getCapacity() < MIN_CAPACITY)
            throw new IllegalArgumentException(
                    "Can't register new warehouse with capacity " + request.getCapacity()  +
                            ": minimum capacity is " + MIN_CAPACITY
            );

        Warehouse entity = mapRequestToEntity(request);
        Long id = repository.save(entity);
        entity.setId(id);
        return mapEntityToResponse(entity);
    }

    public void remove(Long id){
        int affectedRows = repository.remove(id);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't remove warehouse with id" + id + ": no such id in database"
            );
    }

    public void update(WarehouseRequestDto request, Long id){

        Warehouse entity = mapRequestToEntity(request);
        entity.setId(id);

        int affectedRows = repository.update(entity);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't update warehouse with id" + id +
                            ":no such id in database"
            );
    }

    public WarehouseResponseDto findById(Long id){
        Warehouse entity = repository.findById(id).orElseThrow(
                () -> new UnknownEntityException(
                        "Can't find warehouse with id " + id
                )
        );
        return mapEntityToResponse(entity);
    }

    public WarehouseResponseDto findByAddress(Address address){
        Warehouse entity = repository.findByAddress(address).orElseThrow(
                () -> new UnknownEntityException(
                        "Can't find warehouse with address " + address.getValue()
                )
        );
        return mapEntityToResponse(entity);
    }


    private static WarehouseResponseDto mapEntityToResponse(Warehouse entity){
        return new WarehouseResponseDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getName(),
                entity.getAddress(),
                entity.getCapacity()
        );
    }

    private static Warehouse mapRequestToEntity(WarehouseRequestDto request){
        return new Warehouse(
                request.getEmail(),
                request.getPhoneNumber(),
                request.getName(),
                request.getAddress(),
                request.getCapacity()
        );
    }
}
