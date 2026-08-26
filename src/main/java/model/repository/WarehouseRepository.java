package model.repository;

import model.entity.Manufacturer;
import model.entity.Warehouse;
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

public interface WarehouseRepository{

    Id save(Warehouse entity, Connection conn) throws GeneratedKeysException, RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    int setDeletionStatus(boolean status, Id id, Connection conn) throws RepositoryException;
    int remove(Connection conn) throws RepositoryException;
    int update(Warehouse entity, Id id, Connection conn) throws RepositoryException,
            ForeignKeyViolationException, UniqueViolationException;
    List<Warehouse> findAllByDeleteStatus(boolean status, Connection conn) throws RepositoryException;
    Optional<Warehouse> findById(Id id, Connection conn) throws RepositoryException;
    List<Warehouse> findAllByCity(String city, Connection conn) throws RepositoryException;
    List<Warehouse> findAllByCountry(String country, Connection conn) throws RepositoryException;
    List<Warehouse> findAllByRegion(String region, Connection conn) throws RepositoryException;
    Optional<Warehouse> findByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    Optional<Warehouse> findByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByEmail(Email email, Connection conn) throws RepositoryException;
    boolean existsByPhoneNumber(PhoneNumber phoneNumber, Connection conn) throws RepositoryException;
    Optional<Warehouse> findByFullAddress(FullAddress fullAddress, Connection conn) throws RepositoryException;
    boolean existsByFullAddress(FullAddress fullAddress, Connection conn) throws RepositoryException;
    boolean existsById(Id id, Connection conn) throws RepositoryException;
}
