package infrastructure.repositoryImpl.rsmapper;

import model.entity.abstr.BaseEntity;

import java.sql.ResultSet;

public interface RsMapper<T extends BaseEntity> {

    T mapRsToEntity(ResultSet rs);

}
