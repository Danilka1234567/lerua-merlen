package model.repository;

import model.entity.Manufacturer;
import model.exception.ForeignKeyViolationException;
import model.exception.GeneratedKeysException;
import model.exception.RepositoryException;
import model.exception.UniqueViolationException;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository{

    Id save(Manufacturer entity, Connection conn) throws GeneratedKeysException, RepositoryException,
            UniqueViolationException;

    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    int update(Manufacturer entity, Id id, Connection conn) throws RepositoryException, UniqueViolationException;
    List<Manufacturer> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<Manufacturer> findById(Id id, Connection conn) throws RepositoryException;
    List<Manufacturer> findAllBySpecialization(String specialization, Connection conn) throws RepositoryException;
    Optional<Manufacturer> findByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    Optional<Manufacturer> findByEmail(Email email, Connection conn) throws RepositoryException;
    List<Manufacturer> findAllByCity(String city, Connection conn) throws RepositoryException;
    List<Manufacturer> findAllByCountry(String country, Connection conn) throws RepositoryException;
    List<Manufacturer> findAllByRegion(String region, Connection conn) throws RepositoryException;
    Optional<Manufacturer> findByFullAddress(FullAddress fullAddress, Connection conn) throws RepositoryException;
}
