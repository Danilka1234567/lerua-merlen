package infrastructure.repositoryImpl;

import model.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.ExtendedRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.ProductMapper;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.Product;
import model.repository.ProductRepository;
import model.vo.Id;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ProductRepositoryImpl extends ExtendedRepositoryImpl<Product> implements ProductRepository {

    private static final String findAllByWarehouseIdSql = ResourceReader.read(
            "sql/dql/product/select_by_warehouse_id.sql"
    );

    private static final String findAllByManufacturerIdSql = ResourceReader.read(
            "sql/dql/product/select_by_manufacturer_id.sql"
    );

    private static final String findAllByWarehouseIdAndManufacturerIdSql = ResourceReader.read(
            "sql/dql/product/select_by_manufacturer_id_and_warehouse_id.sql"
    );

    private static final RsMapper<Product> mapper = new ProductMapper();

    private static final String updateSql = ResourceReader.read(
            "sql/dml/product/update.sql"
    );

    private static final String findAllByDeleteStatusSql = ResourceReader.read(
            "sql/dql/product/select_by_del_status.sql"
    );

    private static final String findByIdSql = ResourceReader.read(
            "sql/dql/product/select_by_id.sql"
    );

    private static final String saveSql = ResourceReader.read(
            "sql/dml/product/insert.sql"
    );

    private static final String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/product/update_del_status.sql"
    );

    private static final String removeSql = ResourceReader.read(
            "sql/dml/product/delete.sql"
    );

    private static final String existsByIdSql = ResourceReader.read(
            "sql/dql/product/exists_by_id.sql"
    );

    @Override
    protected RsMapper<Product> getMapper() {
        return mapper;
    }

    @Override
    protected String getUpdateSql() {
        return updateSql;
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return findAllByDeleteStatusSql;
    }

    @Override
    protected String getFindByIdSql() {
        return findByIdSql;
    }

    @Override
    protected String getSaveSql() {
        return saveSql;
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return setDeletionStatusSql;
    }

    @Override
    protected String getRemoveSql() {
        return removeSql;
    }

    @Override
    protected void fillUpdatePstmt(PreparedStatement statement, Product entity, Id id) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getWarehouseId().getValue());
        statement.setLong(3, entity.getManufacturerId().getValue());
        statement.setBigDecimal(4, entity.getPrice());
        statement.setBigDecimal(5, entity.getDiscount());
        statement.setLong(6, id.getValue());
    }

    @Override
    protected void fillFindAllByDeleteStatusPstmt(PreparedStatement statement, boolean status) throws SQLException {
        statement.setBoolean(1, status);
    }

    @Override
    protected void fillFindByIdPstmt(PreparedStatement statement, Id id) throws SQLException {
        statement.setLong(1, id.getValue());
    }

    @Override
    protected void fillSaveStatement(PreparedStatement statement, Product entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getWarehouseId().getValue());
        statement.setLong(3, entity.getManufacturerId().getValue());
        statement.setBigDecimal(4, entity.getPrice());
        statement.setBigDecimal(5, entity.getDiscount());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id.getValue());
    }

    @Override
    public List<Product> findAllByWarehouseId(Id warehouseId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByWarehouseIdSql)){
            statement.setLong(1, warehouseId.getValue());

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find products by warehouse id", e
            );
        }
    }

    @Override
    public List<Product> findAllByManufacturerId(Id manufacturerId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByManufacturerIdSql)){
            statement.setLong(1, manufacturerId.getValue());

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find products by manufacturer id", e
            );
        }
    }

    @Override
    public boolean existsById(Id id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, existsByIdSql);
    }
}