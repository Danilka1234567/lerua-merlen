package model.repository.common;

import java.sql.Connection;

public interface ReferencableRepository{

    boolean existsById(Long id, Connection conn);

}
