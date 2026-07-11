package model.repository.implementations.manufacturer;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.Manufacturer;
import model.repository.implementations.crud.CrudRepositoryImpl;
import model.repository.interfaces.ManufacturerRepository;
import model.valueobjects.Address;
import model.valueobjects.Email;
import model.valueobjects.PhoneNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManufacturerRepositoryImpl extends CrudRepositoryImpl<Manufacturer> implements ManufacturerRepository {


    private final static String FIND_ALL_BY_SPECIALIZATION_SQL =
            "SELECT id, name, address, phone_number, email, specialization FROM manufacturers WHERE specialization = ?";

    private final static String FIND_BY_ADDRESS_SQL =
            "SELECT id, name, address, phone_number, email, specialization FROM manufacturers WHERE address = ?";

    private final static String EXISTS_BY_ADDRESS_SQL =
            "SELECT EXISTS(SELECT 1 FROM manufacturers WHERE address = ?)";

    private final static String FIND_BY_EMAIL_SQL =
            "SELECT id, name, address, phone_number, email, specialization FROM manufacturers WHERE email = ?";

    private final static String EXISTS_BY_EMAIL_SQL =
            "SELECT EXISTS(SELECT 1 FROM manufacturers WHERE email = ?)";


    @Override
    protected String getSaveSql() {
        return "INSERT INTO manufacturers (name, address, phone_number, email, specialization) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getFindByIdSql() {
        return "SELECT id, name, address, phone_number, email, specialization FROM manufacturers WHERE id = ?";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE manufacturers SET name = ?, address = ?, phone_number = ?, email = ?, specialization = ? WHERE id = ?";
    }

    @Override
    protected String getRemoveSql() {
        return "DELETE FROM manufacturers WHERE id = ?";
    }

    @Override
    protected void setSaveValues(PreparedStatement statement, Manufacturer entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress().getValue());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
        statement.setString(5, entity.getSpecialization());
    }

    @Override
    protected void setFindByIdValues(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void setUpdateValues(PreparedStatement statement, Manufacturer entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getAddress().getValue());
        statement.setString(3, entity.getPhoneNumber().getValue());
        statement.setString(4, entity.getEmail().getValue());
        statement.setString(5, entity.getSpecialization());
        statement.setLong(6, entity.getId());
    }

    @Override
    protected void setRemoveValues(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected Manufacturer mapResult(ResultSet rs) throws SQLException{
        return new Manufacturer(
                rs.getLong("id"),
                new Email(rs.getString("email")),
                new PhoneNumber(rs.getString("phone_number")),
                rs.getString("name"),
                new Address(rs.getString("address")),
                rs.getString("specialization")
        );
    }

    @Override
    public List<Manufacturer> findAllBySpecialization(String specialization) {

        if (specialization == null){
            throw new IllegalArgumentException(
                    "Can't find manufacturers by specialization: specialization is null"
            );
        }

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_SPECIALIZATION_SQL)){

            statement.setString(1, specialization);

            try(ResultSet rs = statement.executeQuery()){

                List<Manufacturer> result = new ArrayList<>();

                while(rs.next()){
                    Manufacturer manufacturer = mapResult(rs);
                    result.add(manufacturer);
                }

                return result;
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find manufacturers by specialization: " + specialization, e
            );
        }
    }

    @Override
    public Optional<Manufacturer> findByAddress(Address address) {

        if (address == null)
            throw new IllegalArgumentException(
                    "Can't find manufacturer by address: address is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_ADDRESS_SQL)){

            statement.setString(1, address.getValue());

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    return Optional.empty();

                Manufacturer manufacturer = mapResult(rs);
                return Optional.of(manufacturer);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find manufacturer by address: " + address, e
            );
        }

    }

    @Override
    public boolean existsByAddress(Address address) {

        if (address == null)
            throw new IllegalArgumentException(
                    "Can't check existence of manufacturer by address: address is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(EXISTS_BY_ADDRESS_SQL)){

            statement.setString(1, address.getValue());
            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check existence of manufacturer by address: " + address, e
            );
        }
    }

    @Override
    public Optional<Manufacturer> findByEmail(Email email) {

        if (email == null)
            throw new IllegalArgumentException(
                    "Can't find manufacturer by email: email is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_BY_EMAIL_SQL)){

            statement.setString(1, email.getValue());

            try(ResultSet rs = statement.executeQuery()){
                if (! rs.next()){
                    return Optional.empty();
                }

                Manufacturer manufacturer = mapResult(rs);
                return Optional.of(manufacturer);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find manufacturer by email: " + email, e
            );
        }
    }

    @Override
    public boolean existsByEmail(Email email) {
        if (email == null)
            throw new IllegalArgumentException(
                    "Can't check manufacturer by email: email is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(EXISTS_BY_EMAIL_SQL)){

            statement.setString(1, email.getValue());

            try(ResultSet rs = statement.executeQuery()){
                return rs.getBoolean(1);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check manufacturer by email: " + email, e
            );
        }
    }
}
