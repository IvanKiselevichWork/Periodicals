package by.kiselevich.periodicals.specification.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.RepositoryException;
import by.kiselevich.periodicals.pool.ConnectionPool;
import by.kiselevich.periodicals.pool.ConnectionProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindAllThemes implements ThemeSpecification {

    private static final String FIND_ALL_THEMES = "select id, title from edition_theme";

    @Override
    public List<EditionTheme> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<EditionTheme> editionThemes;
        try (ConnectionProxy connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_THEMES);
            resultSet = statement.executeQuery();
            editionThemes = getThemeFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return editionThemes;
    }
}
