package model.repository.common;

import model.entity.abstr.OrganizationEntity;
import model.vo.FullAddress;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface OrganizationRepository<T extends OrganizationEntity> extends ContactableRepository<T> {

    List<T> findAllByCity(String city, Connection conn);
    List<T> findAllByCountry(String country, Connection conn);
    List<T> findAllByRegion(String region, Connection conn);
    Optional<T> findByFullAddress(FullAddress fullAddress, Connection conn);
    boolean existsByFullAddress(FullAddress fullAddress, Connection conn);

}
