package by.kiselevich.periodicals.specification.edition;

import by.kiselevich.periodicals.entity.Edition;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPoolImpl;
import by.kiselevich.periodicals.pool.ConnectionProxy;
import by.kiselevich.periodicals.specification.SpecificationUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link EditionSpecification} for finding all {@link Edition} by {@code id} from database
 */
public class FindEditionById extends SpecificationUtil implements EditionSpecification {

    private static final String FIND_EDITIONS_BY_ID = "select * from edition inner join edition_theme on edition.theme_id = edition_theme.id inner join edition_type on edition.type_id = edition_type.id where edition.id = ?";
    private static final String FIND_NOT_BLOCKED_EDITIONS_BY_ID = "select * from edition inner join edition_theme on edition.theme_id = edition_theme.id inner join edition_type on edition.type_id = edition_type.id where edition.id = ? and is_blocked= false";


    private final int id;
    private final boolean findNotBlockedEditionsOnly;

    public FindEditionById(int id, boolean findNotBlockedEditionsOnly) {
        this.id = id;
        this.findNotBlockedEditionsOnly = findNotBlockedEditionsOnly;
    }

    @Override
    public List<Edition> query() throws RepositoryException {
        String query;
        if (findNotBlockedEditionsOnly) {
            query = FIND_NOT_BLOCKED_EDITIONS_BY_ID;
        } else {
            query = FIND_EDITIONS_BY_ID;
        }
        ResultSet resultSet = null;
        List<Edition> editions = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                editions.add(getEditionFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return editions;
    }
}
