package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Manufacturer;
import model.exception.EntityNotFoundException;
import model.exception.ServiceException;
import model.repository.ManufacturerRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.*;

import java.sql.Connection;
import java.util.List;

public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository){
        Validator.validateNotNull(manufacturerRepository, "Manufacturer service");
        this.manufacturerRepository = manufacturerRepository;
    }

    public Id create(Manufacturer manufacturer){
        Validator.validateNotNull(manufacturer, "Manufacturer");
        Connection conn = ConnectionManager.getConnectionSingletone();
        ContactInfo contactInfo = manufacturer.getContactInfo();

        return Transaction.complete(conn,() ->  manufacturerRepository.save(manufacturer, conn));
    }

    public void updateInfo(Manufacturer manufacturer, Id manufacturerId){
        Validator.validateNotNull(manufacturer, "Manufacturer");
        Validator.validateNotNull(manufacturerId, "Manufacturer id");
        Connection conn = ConnectionManager.getConnectionSingletone();

        Transaction.complete(conn, () -> {
            int affectedRows = manufacturerRepository.update(manufacturer, manufacturerId, conn);

            if (affectedRows == 0)
                throw new ServiceException(
                    "Failed to update manufacturer info"
                );
        });
    }

    public void markAsDeleted(Id id){
        Validator.validateNotNull(id, "Id");
        int affectedRows = manufacturerRepository.setDeletionStatus(false,
                 id, ConnectionManager.getConnectionSingletone());
        if (affectedRows == 0)
            throw new ServiceException("Can't mark manufacturer as deleted");
    }

    public int cleanDeleted(){
        return manufacturerRepository.remove(ConnectionManager.getConnectionSingletone());
    }

    public List<Manufacturer> getAllDeleted(){
        return manufacturerRepository.findAllByDeleteStatus(true, ConnectionManager.getConnectionSingletone());
    }

    public Manufacturer getById(Id id){
        Validator.validateNotNull(id, "Manufacturer id");
        return manufacturerRepository.findById(
                id, ConnectionManager.getConnectionSingletone()
        ).orElseThrow(
                () -> new EntityNotFoundException("Unknown manufacturer id")
        );
    }

    public Manufacturer getByPhoneNumber(PhoneNumber phoneNumber){
        Validator.validateNotNull(phoneNumber, "Phone number");
        return manufacturerRepository.findByPhoneNumber(
                phoneNumber, ConnectionManager.getConnectionSingletone()
        ).orElseThrow(
                () -> new EntityNotFoundException("Unknown manufacturer phone number")
        );
    }

    public Manufacturer getByEmail(Email email){
        Validator.validateNotNull(email, "Email");
        return manufacturerRepository.findByEmail(
                email, ConnectionManager.getConnectionSingletone()
        ).orElseThrow(
                () -> new EntityNotFoundException("Unknown manufacturer email")
        );
    }

    public List<Manufacturer> getAllByCity(String city){
        Validator.validateNotNull(city, "City");
        return manufacturerRepository.findAllByCity(city, ConnectionManager.getConnectionSingletone());
    }

    public List<Manufacturer> getAllByCountry(String country){
        Validator.validateNotNull(country, "Country");
        return manufacturerRepository.findAllByCountry(country, ConnectionManager.getConnectionSingletone());
    }

    public List<Manufacturer> getAllByRegion(String region){
        Validator.validateNotNull(region, "Region");
        return manufacturerRepository.findAllByRegion(region, ConnectionManager.getConnectionSingletone());
    }

    public Manufacturer getByFullAddress(FullAddress fullAddress){
        Validator.validateNotNull(fullAddress, "Full address");
        return manufacturerRepository.findByFullAddress(
                fullAddress, ConnectionManager.getConnectionSingletone()
        ).orElseThrow(
                () -> new EntityNotFoundException("Unknown manufacturer full address")
        );
    }

    public List<Manufacturer> getAllBySpecialization(String specialization){
        Validator.validateNotNull(specialization, "Specialization");
        return manufacturerRepository.findAllBySpecialization(
                specialization, ConnectionManager.getConnectionSingletone()
        );
    }
}
