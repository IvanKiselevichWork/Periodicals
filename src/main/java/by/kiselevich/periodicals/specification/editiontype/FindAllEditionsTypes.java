package by.kiselevich.periodicals.specification.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindAllEditionsTypes implements EditionTypeSpecification {

    private static final String FIND_ALL_EDITIONS_TYPES = "select id, type from edition_type";

    @Override
    public List<EditionType> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<EditionType> editionTypes;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_EDITIONS_TYPES);
            resultSet = statement.executeQuery();
            editionTypes = getEditionsTypesFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return editionTypes;
    }
}
