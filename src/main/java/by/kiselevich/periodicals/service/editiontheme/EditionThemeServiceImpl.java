package by.kiselevich.periodicals.service.editiontheme;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.editiontheme.EditionThemeDao;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import by.kiselevich.periodicals.factory.DaoFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Implementation of {@link EditionThemeService}
 */
public class EditionThemeServiceImpl implements EditionThemeService {

    private static final Logger LOG = LogManager.getLogger(EditionThemeServiceImpl.class);

    private final EditionThemeDao editionThemeDao;

    public EditionThemeServiceImpl() {
        editionThemeDao = DaoFactory.getInstance().getEditionThemeDao();
    }

    @Override
    public List<EditionTheme> getAllThemes() throws ServiceException {
        try {
            return editionThemeDao.getAllEditionThemes();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    public EditionTheme getThemeById(int id) throws ServiceException {
        try {
            return editionThemeDao.getEditionThemeById(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
