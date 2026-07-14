package model.repository.implementations.order;

import infrastructure.config.DataBaseConfig;
import infrastructure.exceptions.repository.RepositoryException;
import model.entities.Order;
import model.repository.implementations.base.BaseRepositoryImpl;
import model.repository.implementations.crud.CrudRepositoryImpl;
import model.repository.interfaces.OrderRepository;
import model.valueobjects.Address;
import utils.enums.OrderStatus;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order> implements OrderRepository {


    private final static String FIND_ALL_BY_ADDRESS_SQL =
            "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE delivery_address = ?";

    private final static String FIND_ALL_BY_USER_ID_SQL =
            "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE user_id = ?";

    private final static String FIND_ALL_BY_PRODUCT_ID_SQL =
            "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE product_id = ?";

    private final static String FIND_ALL_BY_ARRIVING_DATE_SQL =
            "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE creation_date = ? AND delivering_period = ?";

    private final static String FIND_ALL_BY_CREATION_DATE_SQL =
            "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE creation_date = ?";


    @Override
    protected String getExistsByIdSql() {
        return "SELECT EXISTS(SELECT 1 FROM orders WHERE id = ?)";
    }

    @Override
    protected String getSaveSql() {
        return "INSERT INTO orders(user_id, product_id, status, delivery_address, creation_date, delivering_period) VALUES(?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getFindByIdSql() {
        return "SELECT id, user_id, product_id, status, delivery_address, creation_date, delivering_period FROM orders WHERE id = ?";
    }

    @Override
    protected String getUpdateSql() {
        return "UPDATE orders SET user_id = ?, product_id = ?, status = ?, delivering_period = ?, creation_date = ?, last_delivering_day = ? WHERE id = ?";
    }

    @Override
    protected String getRemoveSql() {
        return "DELETE FROM orders WHERE id = ?";
    }

    @Override
    protected void setSaveValues(PreparedStatement statement, Order entity) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getProductId());
        statement.setString(3, entity.getStatus().toString());
        statement.setString(4, entity.getDeliveryAddress().getValue());
        statement.setDate(5, Date.valueOf(entity.getDateOfCreation()));
        statement.setInt(6, entity.getDeliveryPeriod());
    }

    @Override
    protected void setUpdateValues(PreparedStatement statement, Order entity) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getProductId());
        statement.setString(3, entity.getStatus().toString());
        statement.setString(4, entity.getDeliveryAddress().getValue());
        statement.setDate(5, Date.valueOf(entity.getDateOfCreation()));
        statement.setInt(6, entity.getDeliveryPeriod());
        statement.setLong(7, entity.getId());
    }

    @Override
    protected Order mapResult(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("product_id"),
                new Address(rs.getString("delivery_address")),
                LocalDate.ofInstant(rs.getDate("date_of_creation").toInstant(), ZoneId.systemDefault()),
                rs.getInt("delivering_period"),
                OrderStatus.valueOf(rs.getString("status"))
        );
    }

    private List<Order> mapRsToList(ResultSet rs) throws SQLException{
        List<Order> orders = new ArrayList<>();
        while(rs.next()){
            orders.add(mapResult(rs));
        }
        return orders;
    }

    @Override
    public List<Order> findAllByAddress(Address address) {

        if (address == null)
            throw new IllegalArgumentException(
                    "Can't find orders by address: address is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_ADDRESS_SQL)){

            statement.setString(1, address.getValue());

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find orders by address: " + address.getValue(), e
            );
        }
    }

    @Override
    public List<Order> findAllByUserId(Long id) {

        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find orders by user_id : user_id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_USER_ID_SQL)){

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "can't find orders by user_id: " + id, e
            );
        }
    }

    @Override
    public List<Order> findAllByProductId(Long id) {
        if (id == null)
            throw new IllegalArgumentException(
                    "Can't find orders by product_id: product_id is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_PRODUCT_ID_SQL)){
            statement.setLong(1, id);
            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find orders by product_id: " + id, e
            );
        }
    }

    @Override
    public List<Order> findAllByArrivingDate(LocalDate date, int delivering_period) {
        if (date == null)
            throw new IllegalArgumentException(
                    "Can't find orders by arriving date: creation date is null"
            );

        if (delivering_period <= 0)
            throw new IllegalArgumentException(
                    "Can't find orders by arriving date: delivering_period <= 0"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_ARRIVING_DATE_SQL)){

            statement.setDate(1, Date.valueOf(date));
            statement.setInt(2, delivering_period);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }

        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find orders by arriving date: " + date + delivering_period, e
            );
        }
    }

    @Override
    public List<Order> findAllByCreationDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException(
                    "Can't find orders by creation date: creation date is null"
            );

        try(Connection conn = DataBaseConfig.getConnection();
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_CREATION_DATE_SQL)){
                statement.setDate(1, Date.valueOf(date));

                try(ResultSet rs = statement.executeQuery()){
                    return mapRsToList(rs);
                }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't find orders by creation date: " + date, e
            );
        }
    }
}
