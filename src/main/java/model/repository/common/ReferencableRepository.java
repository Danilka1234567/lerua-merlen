package model.repository.common;

import model.exception.RepositoryException;
import model.vo.Id;

import java.sql.Connection;

public interface ReferencableRepository{

    boolean existsById(Id id, Connection conn) throws RepositoryException;

}
