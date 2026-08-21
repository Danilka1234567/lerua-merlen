package infrastructure.repositoryImpl.rsmapper;

import model.entity.Manufacturer;
import model.entity.Product;
import model.entity.Warehouse;
import model.vo.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RsMapper<Product>{

    @Override
    public Product mapRsToEntity(ResultSet rs) throws SQLException {
        return Product.loadFromDb(
                rs.getLong("product_id"),
                rs.getBoolean("product_is_deleted"),
                rs.getDate("product_registration_date").toLocalDate(),
                rs.getLong("warehouse_id"),
                rs.getLong("manufacturer_id"),
                rs.getString("product_name"),
                rs.getBigDecimal("price"),
                rs.getBigDecimal("discount"),
                Warehouse.loadFromDb(
                        rs.getLong("warehouse_id"),
                        rs.getBoolean("warehouse_id_deleted"),
                        rs.getDate("warehouse_registration_date").toLocalDate(),
                        new ContactInfo(
                                new PhoneNumber(rs.getString("warehouse_phone_number")),
                                new Email(rs.getString("warehouse_email"))
                        ),
                        new FullAddress(
                                rs.getString("warehouse_country"),
                                rs.getString("warehouse_region"),
                                rs.getString("warehouse_city"),
                                new StreetAddress("warehouse_street_address")
                        ),
                        rs.getInt("capacity")
                ),
                Manufacturer.loadFromDb(
                        rs.getLong("manufacturer_id"),
                        rs.getBoolean("manufacturer_is_deleted"),
                        rs.getDate("manufacturer_registration_date").toLocalDate(),
                        new ContactInfo(
                                new PhoneNumber(rs.getString("manufacturer_phone_number")),
                                new Email(rs.getString("manufacturer_email"))
                        ),
                        new FullAddress(
                                rs.getString("manufacturer_country"),
                                rs.getString("manufacturer_region"),
                                rs.getString("manufacturer_city"),
                                new StreetAddress(rs.getString("street_address"))
                        ),
                        rs.getString("manufacturer_name"),
                        rs.getString("manufacturer_specialization")
                )
        );
    }
}
