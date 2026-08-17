package infrastructure.repositoryImpl;

import infrastructure.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.ExtendedRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.OrderMapper;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.utils.ResourceReader;
import model.entity.Order;
import model.repository.OrderRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class OrderRepositoryImpl extends ExtendedRepositoryImpl<Order> implements OrderRepository {

    private static final String findAllByProductIdSql = ResourceReader.read(
            "sql/dql/order/select_by_product_id.sql"
    );

    private static final String findAllByUserIdSql = ResourceReader.read(
            "sql/dql/order/select_by_user_id.sql"
    );

    @Override
    protected RsMapper<Order> getMapper() {
        return new OrderMapper();
    }

    @Override
    protected String getFindAllByRegDateSql() {
        return ResourceReader.read("sql/dql/order/select_by_reg_date.sql");
    }

    @Override
    protected String getFindAllBetweenRegDateSql() {
        return ResourceReader.read("sql/dql/order/select_between_reg_dates.sql");
    }

    @Override
    protected String getUpdateSql() {
        return ResourceReader.read("sql/dml/order/update.sql");
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return ResourceReader.read("sql/dql/order/select_by_del_status.sql");
    }

    @Override
    protected String getFindByIdSql() {
        return ResourceReader.read("sql/dql/order/select_by_id.sql");
    }

    @Override
    protected String getRemoveSql() {
        return ResourceReader.read("sql/dml/order/delete.sql");
    }

    @Override
    protected String getSaveSql() {
        return ResourceReader.read("sql/dml/order/insert.sql");
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return ResourceReader.read("sql/dml/order/update_deletion_status.sql");
    }


    @Override
    protected void fillFindAllByRegDatePstmt(PreparedStatement statement, LocalDate date) throws SQLException {
        statement.setDate(1, Date.valueOf(date));
    }

    @Override
    protected void fillFindAllBetweenRegDatePstmt(PreparedStatement statement, LocalDate start, LocalDate end) throws SQLException {
        statement.setDate(1, Date.valueOf(start));
        statement.setDate(2, Date.valueOf(end));
    }

    @Override
    protected void fillUpdatePstmt(PreparedStatement statement, Order entity, Long id) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getProductId());
        statement.setString(3, entity.getDeliveryAddress().getCountry());
        statement.setString(4, entity.getDeliveryAddress().getRegion());
        statement.setString(5, entity.getDeliveryAddress().getCity());
        statement.setString(6, entity.getDeliveryAddress().getStreetAddress().getValue());
        statement.setLong(7, entity.getDeliveryPeriod());
        statement.setLong(8, id);
    }

    @Override
    protected void fillFindAllByDeleteStatusPstmt(PreparedStatement statement, boolean status) throws SQLException {
        statement.setBoolean(1, status);
    }

    @Override
    protected void fillFindByIdPstmt(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    protected void fillSaveStatement(PreparedStatement statement, Order entity) throws SQLException {
        statement.setLong(1, entity.getUserId());
        statement.setLong(2, entity.getProductId());
        statement.setString(3, entity.getDeliveryAddress().getCountry());
        statement.setString(4, entity.getDeliveryAddress().getRegion());
        statement.setString(5, entity.getDeliveryAddress().getCity());
        statement.setString(6, entity.getDeliveryAddress().getStreetAddress().getValue());
        statement.setInt(7, entity.getDeliveryPeriod());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Long id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id);
    }

    @Override
    public List<Order> findAllByProductId(Long productId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByProductIdSql)){
            statement.setLong(1, productId);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find all orders by product id!", e
            );
        }
    }

    @Override
    public List<Order> findAllByUserId(Long userId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByUserIdSql)){
            statement.setLong(1, userId);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        } catch (SQLException e) {
            throw new RepositoryException(
                    "Can't try to find all orders by user id!", e
            );
        }
    }
}
