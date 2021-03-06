package by.kiselevich.periodicals.specification.theme;

import by.kiselevich.periodicals.entity.EditionTheme;
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
 * Implementation of {@link ThemeSpecification} for finding all {@link EditionTheme} by {@code id} from database
 */
public class FindThemeById extends SpecificationUtil implements ThemeSpecification {

    private static final String FIND_THEME_BY_ID = "select * from edition_theme where id = ?";

    private final int id;

    public FindThemeById(int id) {
        this.id = id;
    }

    @Override
    public List<EditionTheme> query() throws RepositoryException {
        ResultSet resultSet = null;
        List<EditionTheme> editionThemes = new ArrayList<>();
        try (ConnectionProxy connection = ConnectionPoolImpl.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_THEME_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                editionThemes.add(getEditionThemeFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
        }
        return editionThemes;
    }
}
