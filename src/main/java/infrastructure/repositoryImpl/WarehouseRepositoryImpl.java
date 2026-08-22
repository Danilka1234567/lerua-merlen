package infrastructure.repositoryImpl;

import infrastructure.repositoryImpl.abstr.OrganizationRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.rsmapper.WarehouseMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.Warehouse;
import model.repository.WarehouseRepository;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class WarehouseRepositoryImpl extends OrganizationRepositoryImpl<Warehouse> implements WarehouseRepository {

    private static final RsMapper<Warehouse> mapper = new WarehouseMapper();

    private static final String existsByFullAddressSql = ResourceReader.read(
            "sql/dql/warehouse/exists_by_full_address.sql"
    );

    private static final String findByFullAddressSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_full_address.sql"
    );

    private static final String findAllByRegionSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_region.sql"
    );

    private static final String findAllByCountrySql = ResourceReader.read(
            "sql/dql/warehouse/select_by_country.sql"
    );

    private static final String findAllByCitySql = ResourceReader.read(
            "sql/dql/warehouse/select_by_city.sql"
    );

    private static final String findByPhoneNumberSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_phone_number.sql"
    );

    private static final String findByEmailSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_email.sql"
    );

    private static final String findAllByRegDateSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_reg_date.sql"
    );

    private static final String findAllBetweenRegDatesSql = ResourceReader.read(
            "sql/dql/warehouse/select_between_reg_dates.sql"
    );

    private static final String updateSql = ResourceReader.read(
            "sql/dml/warehouse/update.sql"
    );

    private static final String findAllByDeleteStatusSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_del_status.sql"
    );

    private static final String findByIdSql = ResourceReader.read(
            "sql/dql/warehouse/select_by_id.sql"
    );

    private static final String saveSql = ResourceReader.read(
            "sql/dml/warehouse/insert.sql"
    );

    private static final String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/warehouse/update_del_status.sql"
    );

    private static final String removeSql = ResourceReader.read(
            "sql/dml/warehouse/remove.sql"
    );

    private static final String existsByIdSql = ResourceReader.read(
            "sql/dql/warehouse/exists_by_id.sql"
    );

    @Override
    protected RsMapper<Warehouse> getMapper() {
        return mapper;
    }

    @Override
    protected String getExistsByFullAddressSql() {
        return existsByFullAddressSql;
    }

    @Override
    protected String getFindByFullAddressSql() {
        return findByFullAddressSql;
    }

    @Override
    protected String getFindAllByRegionSql() {
        return findAllByRegionSql;
    }

    @Override
    protected String getFindAllByCountrySql() {
        return findAllByCountrySql;
    }

    @Override
    protected String getFindAllByCitySql() {
        return findAllByCitySql;
    }

    @Override
    protected String getFindByPhoneNumberSql() {
        return findByPhoneNumberSql;
    }

    @Override
    protected String getFindByEmailSql() {
        return findByEmailSql;
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
    protected String getSaveSql() {
        return saveSql;
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return setDeletionStatusSql;
    }

    @Override
    protected String getRemoveSql() {
        return removeSql;
    }

    @Override
    protected void fillExistsByFullAddressPstmt(PreparedStatement statement, FullAddress fullAddress) throws SQLException {
        statement.setString(1, fullAddress.getCountry());
        statement.setString(2, fullAddress.getRegion());
        statement.setString(3, fullAddress.getCity());
        statement.setString(4, fullAddress.getStreetAddress().getValue());
    }

    @Override
    protected void fillFindByFullAddressPstmt(PreparedStatement statement, FullAddress fullAddress) throws SQLException {
        statement.setString(1, fullAddress.getCountry());
        statement.setString(2, fullAddress.getRegion());
        statement.setString(3, fullAddress.getCity());
        statement.setString(4, fullAddress.getStreetAddress().getValue());
    }

    @Override
    protected void fillFindAllByRegionPstmt(PreparedStatement statement, String region) throws SQLException {
        statement.setString(1, region);
    }

    @Override
    protected void fillFindAllByCountryPstmt(PreparedStatement statement, String country) throws SQLException {
        statement.setString(1, country);
    }

    @Override
    protected void fillFindAllByCityPstmt(PreparedStatement statement, String city) throws SQLException {
        statement.setString(1, city);
    }

    @Override
    protected void fillFindByPhoneNumberPstmt(PreparedStatement statement, PhoneNumber phoneNumber) throws SQLException {
        statement.setString(1, phoneNumber.getValue());
    }

    @Override
    protected void fillFindByEmailPstmt(PreparedStatement statement, Email email) throws SQLException {
        statement.setString(1, email.getValue());
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
    protected void fillUpdatePstmt(PreparedStatement statement, Warehouse entity, Id id) throws SQLException {
        statement.setString(1, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getFullAddress().getCountry());
        statement.setString(4, entity.getFullAddress().getRegion());
        statement.setString(5, entity.getFullAddress().getCity());
        statement.setString(6, entity.getFullAddress().getStreetAddress().getValue());
        statement.setInt(7, entity.getCapacity());
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
    protected void fillSaveStatement(PreparedStatement statement, Warehouse entity) throws SQLException {
        statement.setString(1, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getFullAddress().getCountry());
        statement.setString(4, entity.getFullAddress().getRegion());
        statement.setString(5, entity.getFullAddress().getCity());
        statement.setString(6, entity.getFullAddress().getStreetAddress().getValue());
        statement.setInt(7, entity.getCapacity());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id.getValue());
    }

    @Override
    public boolean existsById(Id id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, existsByIdSql);
    }
}