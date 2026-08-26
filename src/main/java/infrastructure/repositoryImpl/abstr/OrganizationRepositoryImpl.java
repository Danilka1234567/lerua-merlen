package infrastructure.repositoryImpl.abstr;

import model.exception.RepositoryException;
import model.entity.abstr.OrganizationEntity;
import model.vo.FullAddress;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class OrganizationRepositoryImpl<T extends OrganizationEntity> extends ContactableRepositoryImpl<T>{


    protected abstract String getExistsByFullAddressSql();
    protected abstract String getFindByFullAddressSql();
    protected abstract String getFindAllByRegionSql();
    protected abstract String getFindAllByCountrySql();
    protected abstract String getFindAllByCitySql();

    protected abstract void fillExistsByFullAddressPstmt(PreparedStatement statement,
                                                         FullAddress fullAddress) throws SQLException;
    protected abstract void fillFindByFullAddressPstmt(PreparedStatement statement,
                                                       FullAddress fullAddress) throws SQLException;
    protected abstract void fillFindAllByRegionPstmt(PreparedStatement statement,
                                                     String region) throws SQLException;
    protected abstract void fillFindAllByCountryPstmt(PreparedStatement statement,
                                                      String country)throws SQLException;
    protected abstract void fillFindAllByCityPstmt(PreparedStatement statement,
                                                   String city) throws SQLException;



    public boolean existsByFullAddress(FullAddress fullAddress, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getExistsByFullAddressSql())){
            fillExistsByFullAddressPstmt(statement, fullAddress);

            try(ResultSet rs = statement.executeQuery()){

                if (! rs.next())
                    throw new SQLException(
                            "answer from db is empty"
                    );

                return rs.getBoolean(1);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't check entity existence by full address", e
            );
        }
    }


    public Optional<T> findByFullAddress(FullAddress fullAddress, Connection conn) {
        try(PreparedStatement statement = conn.prepareStatement(getFindByFullAddressSql())){
            fillFindByFullAddressPstmt(statement, fullAddress);

            try(ResultSet rs = statement.executeQuery()){

                if (!rs.next())
                    return Optional.empty();

                return Optional.of(getMapper().mapRsToEntity(rs));
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find entity by full address", e
            );
        }
    }


    public List<T> findAllByRegion(String region, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindAllByRegionSql())){
            fillFindAllByRegionPstmt(statement, region);

            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find all entities by region", e
            );
        }
    }


    public List<T> findAllByCountry(String country, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindAllByCountrySql())){
            fillFindAllByCountryPstmt(statement, country);
            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find all entities by country", e
            );
        }
    }


    public List<T> findAllByCity(String city, Connection conn) {

        try(PreparedStatement statement = conn.prepareStatement(getFindAllByCitySql())){
            fillFindAllByCityPstmt(statement, city);
            try(ResultSet rs = statement.executeQuery()){
                return mapRsToList(rs);
            }
        }catch (SQLException e){
            throw new RepositoryException(
                    "Can't try to find all entities by city", e
            );
        }
    }
}
