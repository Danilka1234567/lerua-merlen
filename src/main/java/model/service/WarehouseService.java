package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Warehouse;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.WarehouseRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.util.List;

public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository){
        Validator.validateNotNull(warehouseRepository, "Warehouse repository");
        this.warehouseRepository = warehouseRepository;
    }

    public Id create(Warehouse warehouse){
        Validator.validateNotNull(warehouse, "Warehouse");
        Connection conn = ConnectionManager.getConnectionSingletone();
        return Transaction.complete(conn, () -> warehouseRepository.save(warehouse, conn));
    }

    public void updateInfo(Warehouse warehouse, Id id){
        Validator.validateNotNull(warehouse, "Warehouse");
        Validator.validateNotNull(id, "Id");

        Connection conn = ConnectionManager.getConnectionSingletone();

        Transaction.complete(conn, () -> {
            int affectedRows = warehouseRepository.update(warehouse, id,
                    ConnectionManager.getConnectionSingletone());
            if (affectedRows == 0)
                throw new ServiceException(
                        "Can't update warehouse info"
                );
        });

    }

    public void markAsDeleted(Id id){
        Validator.validateNotNull(id, "Warehouse id");
        int affectedRows = warehouseRepository.setDeletionStatus(true, id,
                ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException(
                    "Can't mark warehouse as deleted"
            );
    }

    public int cleanDeleted(){
        return warehouseRepository.remove(ConnectionManager.getConnectionSingletone());
    }

    public List<Warehouse> getDeleted(){
        return warehouseRepository.findAllByDeleteStatus(true, ConnectionManager.getConnectionSingletone());
    }

    public Warehouse getById(Id id){
        Validator.validateNotNull(id, "Warehouse id");
        return warehouseRepository.findById(id, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown warehouse id")
        );
    }

    public Warehouse getByPhoneNumber(PhoneNumber phoneNumber){
        Validator.validateNotNull(phoneNumber, "Phone number");
        return warehouseRepository.findByPhoneNumber(phoneNumber, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown warehouse phone number")
        );
    }

    public Warehouse getByEmail(Email email){
        Validator.validateNotNull(email, "Email");
        return warehouseRepository.findByEmail(email, ConnectionManager.getConnectionSingletone()).orElseThrow(
                () -> new EntityNotFoundException("Unknown warehouse email")
        );
    }

    public List<Warehouse> getAllByCountry(String country){
        Validator.validateNotNull(country, "Country");
        return warehouseRepository.findAllByCountry(country, ConnectionManager.getConnectionSingletone());
    }

    public List<Warehouse> getAllByRegion(String region){
        Validator.validateNotNull(region, "Region");
        return warehouseRepository.findAllByRegion(region, ConnectionManager.getConnectionSingletone());
    }

    public List<Warehouse> getAllByCity(String city){
        Validator.validateNotNull(city, "City");
        return warehouseRepository.findAllByCity(city, ConnectionManager.getConnectionSingletone());
    }

    public Warehouse getByFullAddress(FullAddress fullAddress){
        Validator.validateNotNull(fullAddress, "Full address");
        return warehouseRepository.findByFullAddress(
                fullAddress, ConnectionManager.getConnectionSingletone()
        ).orElseThrow( () -> new EntityNotFoundException("Unknown full address"));
    }
}
