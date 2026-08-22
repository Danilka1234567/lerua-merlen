package infrastructure.repositoryImpl.rsmapper;

import model.entity.Warehouse;
import model.vo.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WarehouseMapper implements RsMapper<Warehouse> {

    @Override
    public Warehouse mapRsToEntity(ResultSet rs) throws SQLException {
        return Warehouse.loadFromDb(
                new Id(rs.getLong("id")),
                rs.getBoolean("is_deleted"),
                rs.getDate("registration_date").toLocalDate(),
                new ContactInfo(
                        new PhoneNumber(rs.getString("phone_number")),
                        new Email(rs.getString("email"))
                ),
                new FullAddress(
                        rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        new StreetAddress(rs.getString("street_address"))
                ),
                rs.getInt("capacity")
        );
    }
}
