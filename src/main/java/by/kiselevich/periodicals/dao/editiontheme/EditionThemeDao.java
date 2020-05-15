package by.kiselevich.periodicals.dao.editiontheme;

import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;

import java.util.List;

public interface EditionThemeDao {

    /**
     * Get all {@link EditionTheme} from data source
     * @return {@code List} of {@link EditionTheme}
     * @throws DaoException if error occurs
     */
    List<EditionTheme> getAllEditionThemes() throws DaoException;

    /**
     * Get {@link EditionTheme} by {@code id} from data source
     * @param id {@link EditionTheme} {@code id}
     * @return {@link EditionTheme} with given {@code id}
     * @throws DaoException if error occurs
     */
    EditionTheme getEditionThemeById(int id) throws DaoException;
}
