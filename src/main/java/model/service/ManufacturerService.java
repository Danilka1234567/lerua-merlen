package model.service;

import infrastructure.config.ConnectionManager;
import model.entity.Manufacturer;
import model.exception.ForeignKeyViolationException;
import model.exception.RepositoryException;
import model.exception.ServiceException;
import model.exception.UniqueViolationException;
import model.repository.ManufacturerRepository;
import model.service.shared.Transaction;
import model.service.shared.Validator;
import model.vo.ContactInfo;
import model.vo.Id;

import java.sql.Connection;

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


}
