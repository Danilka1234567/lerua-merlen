package model.repository;

import model.entity.Warehouse;
import model.repository.common.OrganizationRepository;
import model.repository.common.ReferencableRepository;

public interface WarehouseRepository extends OrganizationRepository<Warehouse>, ReferencableRepository {


}
