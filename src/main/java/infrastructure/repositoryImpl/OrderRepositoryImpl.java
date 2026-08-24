package infrastructure.repositoryImpl;

import model.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.ExtendedRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.OrderMapper;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.utils.ResourceReader;
import model.entity.Order;
import model.repository.OrderRepository;
import model.vo.Id;

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

    private static final RsMapper<Order> mapper = new OrderMapper();

    private static final String findAllByRegDateSql = ResourceReader.read(
            "sql/dql/order/select_by_reg_date.sql"
    );

    private static final String findAllBetweenRegDatesSql = ResourceReader.read(
            "sql/dql/order/select_between_reg_dates.sql"
    );

    private static final String updateSql = ResourceReader.read(
            "sql/dml/order/update.sql"
    );

    private static final String findAllByDeleteStatusSql = ResourceReader.read(
            "sql/dql/order/select_by_del_status.sql"
    );

    private static final String findByIdSql = ResourceReader.read(
            "sql/dql/order/select_by_id.sql"
    );

    private static final String removeSql = ResourceReader.read(
            "sql/dml/order/delete.sql"
    );

    private static final String saveSql = ResourceReader.read(
            "sql/dml/order/insert.sql"
    );

    private static final String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/order/update_deletion_status.sql"
    );

    @Override
    protected RsMapper<Order> getMapper() {
        return mapper;
    }

    @Override
    protected String getFindAllByRegDateSql() {
        return findAllByRegDateSql;
    }

    @Override
    protected String getFindAllBetweenRegDateSql() {
        return findAllBetweenRegDatesSql;
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
    protected String getRemoveSql() {
        return removeSql;
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
    protected void fillFindAllByRegDatePstmt(PreparedStatement statement, LocalDate date) throws SQLException {
        statement.setDate(1, Date.valueOf(date));
    }

    @Override
    protected void fillFindAllBetweenRegDatePstmt(PreparedStatement statement, LocalDate start, LocalDate end) throws SQLException {
        statement.setDate(1, Date.valueOf(start));
        statement.setDate(2, Date.valueOf(end));
    }

    @Override
    protected void fillUpdatePstmt(PreparedStatement statement, Order entity, Id id) throws SQLException {
        statement.setLong(1, entity.getUserId().getValue());
        statement.setLong(2, entity.getProductId().getValue());
        statement.setString(3, entity.getDeliveryAddress().getCountry());
        statement.setString(4, entity.getDeliveryAddress().getRegion());
        statement.setString(5, entity.getDeliveryAddress().getCity());
        statement.setString(6, entity.getDeliveryAddress().getStreetAddress().getValue());
        statement.setLong(7, entity.getDeliveryPeriod());
        statement.setLong(8, id.getValue());
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
    protected void fillSaveStatement(PreparedStatement statement, Order entity) throws SQLException {
        statement.setLong(1, entity.getUserId().getValue());
        statement.setLong(2, entity.getProductId().getValue());
        statement.setString(3, entity.getDeliveryAddress().getCountry());
        statement.setString(4, entity.getDeliveryAddress().getRegion());
        statement.setString(5, entity.getDeliveryAddress().getCity());
        statement.setString(6, entity.getDeliveryAddress().getStreetAddress().getValue());
        statement.setInt(7, entity.getDeliveryPeriod());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id.getValue());
    }

    @Override
    public List<Order> findAllByProductId(Id productId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByProductIdSql)){
            statement.setLong(1, productId.getValue());

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
    public List<Order> findAllByUserId(Id userId, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllByUserIdSql)){
            statement.setLong(1, userId.getValue());

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