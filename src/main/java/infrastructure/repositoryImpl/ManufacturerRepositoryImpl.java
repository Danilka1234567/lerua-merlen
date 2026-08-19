package infrastructure.repositoryImpl;

import infrastructure.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.OrganizationRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.ManufacturerMapper;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.Manufacturer;
import model.repository.ManufacturerRepository;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.PhoneNumber;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ManufacturerRepositoryImpl extends OrganizationRepositoryImpl<Manufacturer> implements ManufacturerRepository {


    private final String findAllLikeSpecializationSql = ResourceReader.read(
            "sql/dql/manufacturer/select_like_specialization.sql"
    );

    private final String existsByIdSql = ResourceReader.read(
        "sql/dql/manufacturer/select_exists_by_id.sql"
    );

    @Override
    protected RsMapper<Manufacturer> getMapper() {
        return new ManufacturerMapper();
    }

    @Override
    protected String getExistsByFullAddressSql() {
        return ResourceReader.read("sql/dql/manufacturer/exists_by_full_address.sql");
    }

    @Override
    protected String getFindByFullAddressSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_full_address.sql");
    }

    @Override
    protected String getFindAllByRegionSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_region.sql");
    }

    @Override
    protected String getFindAllByCountrySql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_country.sql");
    }

    @Override
    protected String getFindAllByCitySql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_city.sql");
    }

    @Override
    protected String getFindByPhoneNumberSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_phone_number.sql");
    }

    @Override
    protected String getFindByEmailSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_email.sql");
    }

    @Override
    protected String getFindAllByRegDateSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_reg_date.sql");
    }

    @Override
    protected String getFindAllBetweenRegDateSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_between_reg_dates.sql");
    }

    @Override
    protected String getUpdateSql() {
        return ResourceReader.read("sql/dml/manufacturer/update.sql");
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_del_status.sql");
    }

    @Override
    protected String getFindByIdSql() {
        return ResourceReader.read("sql/dql/manufacturer/select_by_id.sql");
    }

    @Override
    protected String getSaveSql() {
        return ResourceReader.read("sql/dml/manufacturer/insert.sql");
    }

    @Override
    protected String getSetDeletionStatusSql() {
        return ResourceReader.read("sql/dml/manufacturer/update_deletion_status.sql");
    }

    @Override
    protected String getRemoveSql() {
        return ResourceReader.read("sql/dml/manufacturer/delete.sql");
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
    protected void fillFindAllByRegDatePstmt(PreparedStatement statement, LocalDate date) throws SQLException {
        statement.setDate(1, Date.valueOf(date));
    }

    @Override
    protected void fillFindAllBetweenRegDatePstmt(PreparedStatement statement, LocalDate start, LocalDate end) throws SQLException {
        statement.setDate(1, Date.valueOf(start));
        statement.setDate(2, Date.valueOf(end));
    }

    @Override
    protected void fillUpdatePstmt(PreparedStatement statement, Manufacturer entity, Long id) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(4, entity.getFullAddress().getCountry());
        statement.setString(5, entity.getFullAddress().getRegion());
        statement.setString(6, entity.getFullAddress().getCity());
        statement.setString(7, entity.getFullAddress().getStreetAddress().getValue());
        statement.setString(8, entity.getSpecialization());
        statement.setLong(9, entity.getId());
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
    protected void fillSaveStatement(PreparedStatement statement, Manufacturer entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(4, entity.getFullAddress().getCountry());
        statement.setString(5, entity.getFullAddress().getRegion());
        statement.setString(6, entity.getFullAddress().getCity());
        statement.setString(7, entity.getFullAddress().getStreetAddress().getValue());
        statement.setString(8, entity.getSpecialization());
    }

    @Override
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Long id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id);
    }

    @Override
    public List<Manufacturer> findAllBySpecialization(String specialization, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(findAllLikeSpecializationSql)){
            statement.setString(1, "%" + specialization + "%");
            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException("Can't try to find manufacturer by specialization", e);
        }
    }

    @Override
    public boolean existsById(Long id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, existsByIdSql);
    }
}
