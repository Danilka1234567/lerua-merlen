package model.repository.common;

import model.vo.Id;

import java.sql.Connection;

public interface ReferencableRepository{

    boolean existsById(Id id, Connection conn);

}
