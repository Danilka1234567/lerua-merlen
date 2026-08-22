package infrastructure.repositoryImpl.rsmapper;

import model.entity.Order;
import model.entity.Product;
import model.entity.User;
import model.enums.UserRole;
import model.vo.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RsMapper<Order> {

    @Override
    public Order mapRsToEntity(ResultSet rs) throws SQLException {
        return Order.loadFromDb(
                new Id(rs.getLong("order_id")),
                rs.getBoolean("order_is_deleted"),
                rs.getDate("order_registration_date").toLocalDate(),
                new Id(rs.getLong("user_id")),
                new Id(rs.getLong("product_id")),
                rs.getInt("delivery_period"),
                new FullAddress(
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        new StreetAddress(rs.getString("street_address"))
                ),
                Product.loadFromDb(
                        new Id(rs.getLong("product_id")),
                        rs.getBoolean("product_is_deleted"),
                        rs.getDate("product_registration_date").toLocalDate(),
                        new Id(rs.getLong("warehouse_id")),
                        new Id(rs.getLong("manufacturer_id")),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price"),
                        rs.getBigDecimal("discount"),
                        null,
                        null
                ),
                User.loadFromDb(
                        new Id(rs.getLong("user_id")),
                        rs.getBoolean("user_is_deleted"),
                        rs.getDate("user_registration_date").toLocalDate(),
                        new ContactInfo(
                                new PhoneNumber(rs.getString("phone_number")),
                                new Email(rs.getString("email"))
                        ),
                        rs.getString("user_name"),
                        null,
                        UserRole.valueOf(rs.getString("role"))
                )
        );
    }
}
