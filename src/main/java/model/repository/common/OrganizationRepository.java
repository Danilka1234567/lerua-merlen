package model.repository.common;

import model.entity.abstr.OrganizationEntity;
import model.exception.RepositoryException;
import model.vo.FullAddress;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface OrganizationRepository<T extends OrganizationEntity> extends ContactableRepository<T> {

    List<T> findAllByCity(String city, Connection conn) throws RepositoryException;
    List<T> findAllByCountry(String country, Connection conn) throws RepositoryException;
    List<T> findAllByRegion(String region, Connection conn) throws RepositoryException;
    Optional<T> findByFullAddress(FullAddress fullAddress, Connection conn) throws RepositoryException;
    boolean existsByFullAddress(FullAddress fullAddress, Connection conn) throws RepositoryException;

}
