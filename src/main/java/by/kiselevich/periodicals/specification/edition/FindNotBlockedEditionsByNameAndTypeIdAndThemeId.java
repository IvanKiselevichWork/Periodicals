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

public class FindNotBlockedEditionsByNameAndTypeIdAndThemeId extends SpecificationUtil implements EditionSpecification {

    private static final String FIND_EDITIONS_BY_NAME = "select * from edition inner join edition_theme on edition.theme_id = edition_theme.id inner join edition_type on edition.type_id = edition_type.id where edition.is_blocked = false";
    private static final String AND_NAME = " and edition.name like ?";
    private static final String AND_TYPE = " and edition_type.id = ?";
    private static final String AND_THEME = " and edition_theme.id = ?";

    private final String name;
    private final Integer typeId;
    private final Integer themeId;

    public FindNotBlockedEditionsByNameAndTypeIdAndThemeId(String name, Integer typeId, Integer themeId) {
        this.name = name;
        this.typeId = typeId;
        this.themeId = themeId;
    }

    @Override
    public List<Edition> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<Edition> editions = new ArrayList<>();
        String query = buildSqlQuery();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            setParametersInPreparedStatement(statement);
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

    private String buildSqlQuery() {
        StringBuilder result = new StringBuilder(FIND_EDITIONS_BY_NAME);
        if (name != null) {
            result.append(AND_NAME);
        }
        if (typeId != null) {
            result.append(AND_TYPE);
        }
        if (themeId != null) {
            result.append(AND_THEME);
        }
        return result.toString();
    }

    private void setParametersInPreparedStatement(PreparedStatement statement) throws SQLException {
        int index = 1;
        if (name != null) {
            statement.setString(index++, name);
        }
        if (typeId != null) {
            statement.setInt(index++, typeId);
        }
        if (themeId != null) {
            statement.setInt(index, themeId);
        }
    }
}
