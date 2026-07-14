package model.repository.implementations.warehouse;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.Warehouse;
import model.repository.implementations.base.BaseRepositoryImpl;
import model.repository.implementations.crud.CrudRepositoryImpl;
import model.repository.interfaces.WarehouseRepository;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

import java.sql.*;
import java.util.Optional;

public class WarehouseRepositoryImpl extends BaseRepositoryImpl<Warehouse> implements WarehouseRepository {


    private final static String FIND_BY_ADDRESS_SQL =
            "SELECT id, name, address, phone_number, email, capacity FROM warehouses WHERE address = ?";

    private final static String EXISTS_BY_ADDRESS_SQL =
            "SELECT EXISTS(SELECT 1 FROM warehouses WHERE address = ?)";

    @Override
    protected String getExistsByIdSql() {
        return "SELECT EXISTS(SELECT 1 FROM warehouses WHERE id = ?)";
    }

    @Override
    protected String getSaveSql() {
        return "INSERT INTO warehouses (name, address, phone_number, email, capacity) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getFindByIdSql() {
        return "SELECT id, name, address, phone_number, email, capacity FROM warehouses WHERE id = ?";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE warehouses SET name = ?, address = ?, phone_number = ?, email = ?, capacity = ? WHERE id = ?";
    }

    @Override
    protected String getRemoveSql() {
        return "DELETE FROM warehouses WHERE id = ?";
    }

    @Override
    protected void setSaveValues(PreparedStatement statement, Warehouse entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress().getValue());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
        statement.setInt(5, entity.getCapacity());
    }

    @Override
    protected void setUpdateValues(PreparedStatement statement, Warehouse entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress().getValue());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
        statement.setInt(5, entity.getCapacity());
        statement.setLong(6, entity.getId());
    }

    @Override
    protected Warehouse mapResult(ResultSet rs) throws SQLException{
        return new Warehouse(
                rs.getLong("id"),
                new Email(rs.getString("email")),
                new PhoneNumber(rs.getString("phone_number")),
                rs.getString("name"),
                new Address(rs.getString("address")),
                rs.getInt("capacity")
        );
    }


    @Override
    public Optional<Warehouse> findByAddress(Address address) {

        if (address == null)
            throw new IllegalArgumentException(
                    "Can't find warehouse by address: address is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_ADDRESS_SQL)){

            statement.setString(1, address.getValue());

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                Warehouse warehouse = mapResult(rs);
                return Optional.of(warehouse);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find warehouse by address: " + address, e
            );
        }
    }

    @Override
    public boolean existsByAddress(Address address) {
        if (address == null)
            throw new IllegalArgumentException(
                    "Can't check existence of warehouse by address: address is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(EXISTS_BY_ADDRESS_SQL)){

            statement.setString(1, address.getValue());
            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check existence of warehouse by address:" + address, e
            );
        }
    }
}
