package infrastructure.repositoryImpl;

import infrastructure.repositoryImpl.abstr.OrganizationRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.Warehouse;
import model.repository.WarehouseRepository;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.PhoneNumber;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class WarehouseRepositoryImpl extends OrganizationRepositoryImpl<Warehouse> implements WarehouseRepository {

    @Override
    protected RsMapper<Warehouse> getMapper() {
        return null;
    }

    @Override
    protected String getExistsByFullAddressSql() {
        return ResourceReader.read(
                "sql/dql/warehouse/exists_by_full_address.sql"
        );
    }

    @Override
    protected String getFindByFullAddressSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_full_address.sql");
    }

    @Override
    protected String getFindAllByRegionSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_region.sql");
    }

    @Override
    protected String getFindAllByCountrySql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_country.sql");
    }

    @Override
    protected String getFindAllByCitySql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_city.sql");
    }

    @Override
    protected String getFindByPhoneNumberSql(){
        return ResourceReader.read("sql/dql/warehouse/select_by_phone_number.sql");
    }

    @Override
    protected String getFindByEmailSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_email.sql");
    }

    @Override
    protected String getFindAllByRegDateSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_reg_date.sql");
    }

    @Override
    protected String getFindAllBetweenRegDateSql() {

        return ResourceReader.read("sql/dql/warehouse/select_between_reg_dates.sql");
    }

    @Override
    protected String getUpdateSql() {
        return ResourceReader.read("sql/dml/warehouse/update.sql");
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_del_status.sql");
    }

    @Override
    protected String getFindByIdSql() {
        return ResourceReader.read("sql/dql/warehouse/select_by_id.sql");
    }


    @Override
    protected String getSaveSql() {
        return ResourceReader.read("sql/dml/warehouse/insert.sql");
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return ResourceReader.read("sql/dml/warehouse/update_del_status.sql");
    }

    @Override
    protected String getRemoveSql() {
        return ResourceReader.read("sql/dml/warehouse/remove.sql");
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
    protected void fillUpdatePstmt(PreparedStatement statement, Warehouse entity, Long id) throws SQLException {
        statement.setString(1, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getFullAddress().getCountry());
        statement.setString(4, entity.getFullAddress().getRegion());
        statement.setString(5, entity.getFullAddress().getCity());
        statement.setString(6, entity.getFullAddress().getStreetAddress().getValue());
        statement.setInt(7, entity.getCapacity());
        statement.setLong(8, entity.getId());
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
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Long id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id);
    }

    @Override
    public boolean existsById(Long id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, ResourceReader.read("sql/dql/warehouse/exists_by_id.sql"));
    }
}
