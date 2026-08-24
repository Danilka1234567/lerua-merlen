package model.repository;

import model.entity.Manufacturer;
import model.exception.RepositoryException;
import model.repository.common.OrganizationRepository;
import model.repository.common.ReferencableRepository;

import java.sql.Connection;
import java.util.List;

public interface ManufacturerRepository extends OrganizationRepository<Manufacturer>, ReferencableRepository {

    List<Manufacturer> findAllBySpecialization(String specialization, Connection conn) throws RepositoryException;

}
