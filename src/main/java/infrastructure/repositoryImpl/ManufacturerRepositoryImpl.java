package infrastructure.repositoryImpl;

import model.exception.RepositoryException;
import infrastructure.repositoryImpl.abstr.OrganizationRepositoryImpl;
import infrastructure.repositoryImpl.rsmapper.ManufacturerMapper;
import infrastructure.repositoryImpl.rsmapper.RsMapper;
import infrastructure.repositoryImpl.shared.ExistenceChecker;
import infrastructure.utils.ResourceReader;
import model.entity.Manufacturer;
import model.repository.ManufacturerRepository;
import model.vo.Email;
import model.vo.FullAddress;
import model.vo.Id;
import model.vo.PhoneNumber;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ManufacturerRepositoryImpl extends OrganizationRepositoryImpl<Manufacturer> implements ManufacturerRepository {


    private final static String findAllLikeSpecializationSql = ResourceReader.read(
            "sql/dql/manufacturer/select_like_specialization.sql"
    );

    private final static String existsByIdSql = ResourceReader.read(
        "sql/dql/manufacturer/select_exists_by_id.sql"
    );

    private final static RsMapper<Manufacturer> mapper = new ManufacturerMapper();

    private final static String existsByFullAddressSql = ResourceReader.read(
            "sql/dql/manufacturer/exists_by_full_address.sql"
    );

    private final static String findByFullAddressSql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_full_address.sql"
    );

    private final static String findAllByRegionSql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_region.sql"
    );

    private final static String findAllByCountrySql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_country.sql"
    );

    private final static String findAllByCitySql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_city.sql"
    );

    private final static String findByPhoneNumberSql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_phone_number.sql"
    ) ;

    private final static String findByEmailSql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_email.sql"
    );

    private final static String updateSQl = ResourceReader.read("sql/dml/manufacturer/update.sql");

    private final static String findAllBetweenDelStatusSql = ResourceReader.read(
            "sql/dql/manufacturer/select_by_del_status.sql"
    );

    private final static String findByIdSql = ResourceReader.read("sql/dql/manufacturer/select_by_id.sql");

    private final static String saveSql = ResourceReader.read("sql/dml/manufacturer/insert.sql");

    private final static String setDeletionStatusSql = ResourceReader.read(
            "sql/dml/manufacturer/update_deletion_status.sql"
    );

    private final static String removeSql = ResourceReader.read("sql/dml/manufacturer/delete.sql");

    @Override
    protected RsMapper<Manufacturer> getMapper() {
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
    protected String getExistsByEmailSql() {
        return "SELECT EXISTS(SELECT 1 FROM manufacturers WHERE email = ? AND is_deleted = false)";
    }

    @Override
    protected String getExistsByPhoneNumberSql() {
        return "SELECT EXISTS(SELECT 1 FROM manufacturers WHERE phone_number = ? AND is_deleted = false)";
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
    protected String getUpdateSql() {
        return updateSQl;
    }

    @Override
    protected String getFindAllByDeleteStatusSql() {
        return findAllBetweenDelStatusSql;
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
    protected void fillUpdatePstmt(PreparedStatement statement, Manufacturer entity, Id id) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getContactInfo().getEmail().getValue());
        statement.setString(3, entity.getContactInfo().getPhoneNumber().getValue());
        statement.setString(4, entity.getFullAddress().getCountry());
        statement.setString(5, entity.getFullAddress().getRegion());
        statement.setString(6, entity.getFullAddress().getCity());
        statement.setString(7, entity.getFullAddress().getStreetAddress().getValue());
        statement.setString(8, entity.getSpecialization());
        statement.setLong(9, id.getValue());
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
    protected void fillSetDeletionStatusStatement(PreparedStatement statement, Id id, boolean deletionStatus) throws SQLException {
        statement.setBoolean(1, deletionStatus);
        statement.setLong(2, id.getValue());
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
    public boolean existsById(Id id, Connection conn) {
        return ExistenceChecker.checkExistenceById(conn, id, existsByIdSql);
    }
}
