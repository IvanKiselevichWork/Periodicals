package by.kiselevich.periodicals.service.editiontheme;

import by.kiselevich.periodicals.command.ResourceBundleMessages;
import by.kiselevich.periodicals.dao.editiontheme.EditionThemeDao;
import by.kiselevich.periodicals.entity.EditionTheme;
import by.kiselevich.periodicals.exception.DaoException;
import by.kiselevich.periodicals.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link EditionThemeService}
 */
@Service
public class EditionThemeServiceImpl implements EditionThemeService {

    private static final Logger LOG = LogManager.getLogger(EditionThemeServiceImpl.class);

    private EditionThemeDao editionThemeDao;

    public void setEditionThemeDao(EditionThemeDao editionThemeDao) {
        this.editionThemeDao = editionThemeDao;
    }

    @Override
    @Transactional
    public List<EditionTheme> getAllThemes() throws ServiceException {
        try {
            return editionThemeDao.getAllEditionThemes();
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }

    @Override
    @Transactional
    public EditionTheme getThemeById(int id) throws ServiceException {
        try {
            return editionThemeDao.getEditionThemeById(id);
        } catch (DaoException e) {
            LOG.warn(e);
            throw new ServiceException(ResourceBundleMessages.INTERNAL_ERROR.getKey());
        }
    }
}
