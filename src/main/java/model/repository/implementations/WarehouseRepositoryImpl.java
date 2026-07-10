package model.repository.implementations;

import infrastructure.config.DataBaseConfig;
import infrastructure.config.Transaction;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.Warehouse;
import model.repository.interfaces.WarehouseRepository;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

import java.sql.*;
import java.util.Optional;

public class WarehouseRepositoryImpl implements WarehouseRepository {

    private final static String SAVE_SQL =
            "INSERT INTO warehouses (name, address, phone_number, email, capacity) VALUES (?, ?, ?, ?, ?)";

    private final static String FIND_BY_ID_SQL =
            "SELECT id, name, address, phone_number, email, capacity FROM warehouses WHERE id = ?";

    private final static String UPDATE_SQL =
            "UPDATE warehouses SET name = ?, address = ?, phone_number = ?, email = ?, capacity = ? WHERE id = ?";

    private final static String REMOVE_SQL =
            "DELETE FROM warehouses WHERE id = ?";

    private final static String FIND_BY_ADDRESS_SQL =
            "SELECT id, name, address, phone_number, email, capacity FROM warehouses WHERE address = ?";

    private final static String EXISTS_BY_ADDRESS_SQL =
            "SELECT EXISTS(SELECT 1 FROM warehouses where address = ? LIMIT 1)";


    @Override
    public long save(Warehouse obj) {

        if (obj == null)
            throw new IllegalArgumentException(
                    "Can't save warehouse: warehouse is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){

                    statement.setString(1, obj.getName());
                    statement.setString(2, obj.getAddress().getValue());
                    statement.setString(3, obj.getPhoneNumber().getValue());
                    statement.setString(4, obj.getEmail().getValue());
                    statement.setInt(5, obj.getCapacity());

                    statement.executeUpdate();

                    try(ResultSet rs = statement.getGeneratedKeys()){
                        if (! rs.next())
                            throw new SQLException(
                                    "Can't receive generated value"
                            );
                        return rs.getLong(1);
                    }
                }
            } );

        } catch (SQLException e) {
            throw new RepositoryException(
                    "Can't save warehouse with address: " + obj.getAddress(), e
            );
        }
    }

    @Override
    public Optional<Warehouse> findById(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find warehouse by id: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_ID_SQL)){

            statement.setLong(1, id);
            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                Warehouse warehouse = mapResult(rs);
                return Optional.of(warehouse);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find warehouse by id: " + id, e
            );
        }
    }

    @Override
    public int update(Warehouse obj) {

        if (obj == null)
            throw new IllegalArgumentException(
                    "Can't update warehouse: warehouse is null"
            );

        if (obj.getId() == null)
            throw new IllegalArgumentException(
                    "Can't update warehouse: warehouse's id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){
            return Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)){

                    statement.setString(1, obj.getName());
                    statement.setString(2, obj.getAddress().getValue());
                    statement.setString(3, obj.getPhoneNumber().getValue());
                    statement.setInt(4, obj.getCapacity());
                    statement.setLong(5, obj.getId());

                    int affectedRows = statement.executeUpdate();

                    if (affectedRows == 0)
                        throw new SQLException(
                                "Unknown warehouse"
                        );

                    return affectedRows;
                }
            });

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't update warehouse with id " + obj.getId(), e
            );
        }
    }

    @Override
    public void remove(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                "Can't remove warehouse: id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection()){

            Transaction.execute(conn, () -> {
                try(PreparedStatement statement = conn.prepareStatement(REMOVE_SQL)){
                    statement.setLong(1, id);

                    int affectedRows = statement.executeUpdate();

                    if (affectedRows == 0)
                        throw new SQLException(
                                "Unknown id"
                        );
                }
            });

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't remove warehouse with id: " + id, e
            );
        }
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

    private Warehouse mapResult(ResultSet rs) throws SQLException{
        return new Warehouse(
                new Email(rs.getString("email")),
                new PhoneNumber(rs.getString("phone_number")),
                rs.getString("name"),
                new Address(rs.getString("address")),
                rs.getInt("capacity")
        );
    }
}
