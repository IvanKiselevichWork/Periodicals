package by.kiselevich.periodicals.specification.editiontype;

import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindEditionTypeById implements EditionTypeSpecification {

    private static final String FIND_EDITION_TYPE_BY_ID = "select id, type from edition_type where id = ?";

    private int id;

    public FindEditionTypeById(int id) {
        this.id = id;
    }

    @Override
    public List<EditionType> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<EditionType> editionTypes;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_EDITION_TYPE_BY_ID);
            statement.setInt(1, id);
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
