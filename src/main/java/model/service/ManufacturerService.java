package model.service;

import infrastructure.exceptions.service.EntityAlreadyExistsException;
import infrastructure.exceptions.service.UnknownEntityException;
import model.dto.request.ManufacturerRequestDto;
import model.dto.response.ManufacturerResponseDto;
import model.entities.Manufacturer;
import model.repository.implementations.manufacturer.ManufacturerRepositoryImpl;
import model.repository.interfaces.ManufacturerRepository;
import model.valueobjects.Address;
import model.valueobjects.Email;

import java.util.List;

public class ManufacturerService {

    private final ManufacturerRepository repository = new ManufacturerRepositoryImpl();

    public ManufacturerResponseDto register(ManufacturerRequestDto request){

        if (repository.existsByAddress(request.getAddress()))
            throw new EntityAlreadyExistsException(
                    "Can't register new manufacturer: manufacturer with address" + request.getAddress().getValue() + "is already in database"
            );

        if (repository.existsByEmail(request.getEmail()))
            throw new EntityAlreadyExistsException(
                    "Can't register new manufacturer: manufacturer with email" + request.getEmail() + "is already in database"
            );

        Manufacturer entity = mapRequestToEntity(request);
        long id = repository.save(entity);
        entity.setId(id);
        return mapEntityToResponse(entity);
    }

    public void remove(Long id){
        int affectedRows = repository.remove(id);
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't remove manufacturer with id  " + id + ": can't find such id in database"
            );
    }

    public void update(ManufacturerRequestDto request, Long id){
        int affectedRows = repository.update(mapRequestToEntity(request));
        if (affectedRows == 0)
            throw new UnknownEntityException(
                    "Can't update manufacturer with id "
            );
    }

    public ManufacturerResponseDto findById(Long id){
        Manufacturer entity = repository.findById(id).orElseThrow(
                () -> new UnknownEntityException(
                        "Can't find manufacturer with id " + id + " in database"
                )
        );
        return mapEntityToResponse(entity);
    }

    public ManufacturerResponseDto findByEmail(Email email){
        Manufacturer entity = repository.findByEmail(email).orElseThrow(
                () -> new UnknownEntityException(
                        "Can't find manufacturer with email " + email.getValue() + " in database"
                )
        );
        return mapEntityToResponse(entity);
    }

    public ManufacturerResponseDto findByAddress(Address address){
        Manufacturer entity = repository.findByAddress(address).orElseThrow(
                () -> new UnknownEntityException(
                        "Can;t find manufacturer with address " + address.getValue() + "in database"
                )
        );
        return mapEntityToResponse(entity);
    }

    public List<ManufacturerResponseDto> findAllBySpecialization(String spec){
        List<Manufacturer> manufacturers = repository.findAllBySpecialization(spec);
        return manufacturers.stream().map(ManufacturerService::mapEntityToResponse).toList();
    }

    private static Manufacturer mapRequestToEntity(ManufacturerRequestDto request){
        return new Manufacturer(
                request.getEmail(),
                request.getPhoneNumber(),
                request.getName(),
                request.getAddress(),
                request.getSpecialization()
        );
    }

    private static ManufacturerResponseDto mapEntityToResponse(Manufacturer entity){
        return new ManufacturerResponseDto(
                entity.getId(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getName(),
                entity.getAddress()
        );
    }

}
