package model.repository.implementations.product;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.Product;
import model.repository.implementations.base.BaseRepositoryImpl;
import model.repository.implementations.crud.CrudRepositoryImpl;
import model.repository.interfaces.ProductRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl extends BaseRepositoryImpl<Product> implements ProductRepository {


    private final static String FIND_ALL_BY_MANUFACTURER_ID_SQL ="""
                SELECT id, name, manufacturer_id, warehouse_id, date_of_manufacturing, date_of_placement_to_warehouse, cost, discount
                FROM products WHERE manufacturer_id = ?""";

    private final static String FIND_ALL_BY_WAREHOUSE_ID_SQL = """
                SELECT id, name, manufacturer_id, warehouse_id, date_of_manufacturing, date_of_placement_to_warehouse, cost, discount
                FROM products WHERE warehouse_id = ?""";

    @Override
    protected String getExistsByIdSql() {
        return "SELECT EXISTS(SELECT 1 FROM products WHERE id = ?)";
    }

    @Override
    protected String getSaveSql() {
        return """
                INSERT INTO products (
                name,
                manufacturer_id,
                warehouse_id,
                date_of_manufacturing,
                date_of_placement_to_warehouse,
                cost,
                discount)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";
    }

    @Override
    protected String getFindByIdSql() {
        return """
                SELECT id, name, manufacturer_id, warehouse_id, date_of_manufacturing, date_of_placement_to_warehouse, cost, discount
                FROM products WHERE id = ?""";
    }

    @Override
    protected String getUpdateSql() {
        return """
                UPDATE products
                SET name = ?, manufacturer_id = ?, warehouse_id = ?, date_of_manufacturing = ?, date_of_placement_to_warehouse = ?, cost = ?, discount = ?
                WHERE id = ?""";
    }

    @Override
    protected String getRemoveSql() {
        return "DELETE FROM products WHERE id = ?";
    }

    @Override
    protected void setSaveValues(PreparedStatement statement, Product entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getManufacturerId());
        statement.setLong(3, entity.getWarehouseId());
        statement.setDate(4, Date.valueOf(entity.getDateOfManufacturing()));
        statement.setDate(5, Date.valueOf(entity.getDateOfPlacementToWarehouse()));
        statement.setBigDecimal(6, entity.getPrice());
        statement.setBigDecimal(7, entity.getDiscount());
    }

    @Override
    protected void setUpdateValues(PreparedStatement statement, Product entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getManufacturerId());
        statement.setLong(3, entity.getWarehouseId());
        statement.setDate(4, Date.valueOf(entity.getDateOfManufacturing()));
        statement.setDate(5, Date.valueOf(entity.getDateOfPlacementToWarehouse()));
        statement.setBigDecimal(6, entity.getPrice());
        statement.setBigDecimal(7, entity.getDiscount());
    }

    @Override
    protected Product mapResult(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("manufacturer_id"),
                rs.getLong("warehouse_id"),
                LocalDate.ofInstant(rs.getDate("date_of_manufacturing").toInstant(), ZoneId.systemDefault()),
                LocalDate.ofInstant(rs.getDate("date_of_placement_to_warehouse").toInstant(), ZoneId.systemDefault()),
                rs.getBigDecimal("cost"),
                rs.getBigDecimal("discount")
        );
    }

    @Override
    public List<Product> findAllByManufacturerId(Long id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find products by manufacturer_id: manufacturer_id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_MANUFACTURER_ID_SQL)){

            statement.setLong(1, id);

            try(ResultSet rs = statement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while(rs.next()){
                    products.add(mapResult(rs));
                }
                return products;
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find products by manufacturer_id: " + id, e
            );
        }

    }

    @Override
    public List<Product> findAllByWarehouseId(Long id) {
        if (id == null){
            throw new IllegalArgumentException(
                    "Can't find products by warehouse_id: warehouse_id is null"
            );
        }

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_WAREHOUSE_ID_SQL)){

            statement.setLong(1, id);

            try(ResultSet rs = statement.executeQuery()){
                List<Product> products = new ArrayList<>();
                while(rs.next()){
                    products.add(mapResult(rs));
                }
                return products;
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find products by warehouse_id: " + id, e
            );
        }
    }
}
