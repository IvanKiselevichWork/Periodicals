package by.kiselevich.periodicals.service.editiontheme;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.entity.EditionType;
import by.kiselevich.periodicals.exception.ServiceException;

import java.util.List;

/**
 * Service to work with {@link EditionType}
 */
public interface EditionThemeService {

    /**
     * Returns all {@link EditionTheme} from data source
     * @return {@link List} with all {@link EditionTheme}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    List<EditionTheme> getAllThemes() throws ServiceException;

    /**
     * Returns {@link EditionTheme} from data source  by its {@code id}
     * @param id {@link EditionTheme} {@code id}
     * @return {@link EditionTheme}
     * @throws ServiceException with {@link ResourceBundleMessages} key as message to view error message to user if error occurs
     */
    EditionTheme getThemeById(int id) throws ServiceException;
}
